package br.com.rotafood.api.order.application.service;

import br.com.rotafood.api.common.application.service.CustomerService;
import br.com.rotafood.api.infra.config.rabbitmq.RabbitQueueManager;
import br.com.rotafood.api.infra.config.redis.RecentOrderCacheService;
import br.com.rotafood.api.infra.utils.DateUtils;
import br.com.rotafood.api.merchant.domain.entity.Merchant;
import br.com.rotafood.api.merchant.domain.repository.MerchantRepository;
import br.com.rotafood.api.order.domain.repository.CommandRepository;
import br.com.rotafood.api.order.domain.repository.OrderRepository;
import br.com.rotafood.api.order.application.dto.CommandDto;
import br.com.rotafood.api.order.application.dto.FullOrderDto;
import br.com.rotafood.api.order.domain.entity.Command;
import br.com.rotafood.api.order.domain.entity.Order;
import br.com.rotafood.api.order.domain.entity.OrderStatus;
import br.com.rotafood.api.order.domain.entity.OrderType;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    
    @Autowired private OrderRepository orderRepository;
    @Autowired private MerchantRepository merchantRepository;
    @Autowired private OrderTotalService orderTotalService;
    @Autowired private CustomerService customerService;
    @Autowired private OrderScheduleService orderScheduleService;
    @Autowired private OrderDeliveryService orderDeliveryService;
    @Autowired private OrderTakeoutService orderTakeoutService;
    @Autowired private PaymentRecordService orderPaymentService;
    @Autowired private OrderItemService orderItemService;
    @Autowired private OrderAdditionalFeeService orderAdditionalFeeService;
    @Autowired private OrderBenefitService orderBenefitService;
    @Autowired private CommandRepository commandRepository;
    @Autowired private RabbitTemplate rabbitTemplate;
    @Autowired private RecentOrderCacheService recentOrderCacheService;
    @Autowired private RabbitQueueManager rabbitQueueManager;

    @Value("${api.security.allowed.origin}")
    private String allowedOrigin;


    public List<Order> getAllByMerchantId(UUID merchantId) {
        return orderRepository.findAllByMerchantId(merchantId);
    }

    public Order getByIdAndMerchantId(UUID orderId, UUID merchantId) {
        return orderRepository.findByIdAndMerchantId(orderId, merchantId)
                .orElseThrow(() -> new EntityNotFoundException("Order não encontrado."));
    }

    @Transactional
    public Order createOrUpdate(FullOrderDto dto, UUID merchantId) {
        var merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new EntityNotFoundException("Merchant não encontrado."));

        Order order = dto.id() != null
                ? getByIdAndMerchantId(dto.id(), merchantId)
                : new Order();

        this.updateOrderFields(order, dto, merchant, false);
        orderRepository.save(order);
        this.synchronizeOrderDetails(dto, order);

        if (this.shouldNotifyKitchen(dto.status())) {
            this.notifyKitchen(order);
        }

        orderRepository.save(order);

        this.recentOrderCacheService.addOrUpdateRecentOrder(merchantId, new FullOrderDto(order));

        return order;
    }


    @Transactional
    public Order createFromCatalogOnline(FullOrderDto dto, UUID merchantId) {
        if (dto.id() != null)
            throw new ValidationException("Não é permitido enviar um ID na criação online.");

        var merchant = getOpenedMerchant(merchantId);
        Order order = new Order();
        updateOrderFields(order, dto, merchant, true);
        orderRepository.save(order);
        synchronizeOrderDetails(dto, order);

        if (this.shouldNotifyKitchen(dto.status())) {
            this.notifyKitchen(order);
        }

        return order;
    }

    private void updateOrderFields(Order order, FullOrderDto dto, Merchant merchant, boolean validateRoute) {
        order.setPreparationStartDateTime(dto.preparationStartDateTime() != null
                ? dto.preparationStartDateTime().toInstant() : Instant.now());
        order.setSalesChannel(dto.salesChannel());
        order.setTiming(dto.timing());
        order.setType(dto.type());
        order.setCreatedAt(Instant.now());
        order.setStatus(dto.status());
        order.setExtraInfo(dto.extraInfo());
        order.setMerchant(merchant);
        order.setTotal(orderTotalService.validateAndCalculateTotal(dto, merchant, validateRoute));
        order.setDelivery(dto.delivery() != null ? orderDeliveryService.createOrUpdate(dto.delivery()) : null);
        order.setSchedule(dto.schedule() != null ? orderScheduleService.createOrUpdate(dto.schedule()) : null);
        order.setTakeout(dto.takeout() != null ? orderTakeoutService.createOrUpdate(dto.takeout()) : null);
        order.setPayment(dto.payment() != null ? orderPaymentService.createOrUpdate(dto.payment()) : null);
        order.setMerchantSequence(dto.merchantSequence() != null ? dto.merchantSequence() : getNextMerchantSequence(merchant.getId()));
        order.setCustomer(dto.customer() != null
                ? customerService.createOrUpdateWithAddressIfDelivery(dto.customer(), dto.delivery() != null ? dto.delivery().address() : null)
                : null);
        order.setCommand(dto.command() != null ? addOrderToCommand(order, dto.command()) : null);
    }

    private void synchronizeOrderDetails(FullOrderDto dto, Order order) {
        orderItemService.synchronizeItems(dto.items(), order);
        if (dto.additionalFees() != null)
            orderAdditionalFeeService.synchronizeAdditionalFees(dto.additionalFees(), order);
        if (dto.benefits() != null)
            orderBenefitService.synchronizeBenefits(dto.benefits(), order);
    }

    @Transactional
    public Command addOrderToCommand(Order order, CommandDto commandDto) {
        var command = commandRepository.findByIdAndMerchantId(commandDto.id(), order.getMerchant().getId()).orElseThrow(
            () -> new EntityNotFoundException("Comanda não encontrada"));
        command.getOrders().add(order);
        order.setCommand(command);
        return command;
    }

    @Transactional
    public void updateOrderStatus(UUID merchantId, UUID orderId, OrderStatus status) {
        Order order = getByIdAndMerchantId(orderId, merchantId);
        order.setStatus(status);
        orderRepository.save(order);

        recentOrderCacheService.addOrUpdateRecentOrder(merchantId, new FullOrderDto(order));
        if (shouldNotifyKitchen(status))
            notifyKitchen(order);
    }

    @Transactional
    public void deleteByIdAndMerchantId(UUID orderId, UUID merchantId) {
        Order order = getByIdAndMerchantId(orderId, merchantId);
        orderRepository.delete(order);
    }

    public Page<Order> getAllByFilters(UUID merchantId, List<OrderType> orderTypes, List<OrderStatus> orderStatus,
                                       String startDate, String endDate, Pageable pageable) {
        Instant start = DateUtils.parseDateStringToInstant(startDate, false);
        Instant end = DateUtils.parseDateStringToInstant(endDate, true);
        return orderRepository.findAllByFilters(merchantId, orderTypes, orderStatus, start, end, pageable);
    }

    public List<FullOrderDto> polling(UUID merchantId) {
        merchantRepository.updateLastOpenedUtc(merchantId, Instant.now());

        List<FullOrderDto> cachedOrders = recentOrderCacheService.getCachedRecentOrders(merchantId);

        if (cachedOrders.isEmpty()) {
            Instant twoHoursAgo = Instant.now().minus(2, ChronoUnit.HOURS);
            List<OrderStatus> activeStatuses = List.of(
                    OrderStatus.CREATED, OrderStatus.CONFIRMED, OrderStatus.PREPARATION_STARTED,
                    OrderStatus.READY_TO_PICKUP, OrderStatus.DISPATCHED
            );
            cachedOrders = orderRepository
                    .findRecentOrdersByStatus(merchantId, activeStatuses, twoHoursAgo)
                    .stream().map(FullOrderDto::new).toList();

            recentOrderCacheService.cacheRecentOrders(merchantId, cachedOrders);
        }

        return cachedOrders;
    }

    private Long getNextMerchantSequence(UUID merchantId) {
        List<FullOrderDto> cachedOrders = recentOrderCacheService.getCachedRecentOrders(merchantId);

        if (cachedOrders != null && !cachedOrders.isEmpty()) {
            return cachedOrders.stream()
                    .map(FullOrderDto::merchantSequence)
                    .filter(seq -> seq != null)
                    .max(Long::compareTo)
                    .orElse(0L) + 1;
        }

        Long lastSeq = orderRepository.findMaxMerchantSequenceByMerchantId(merchantId);
        return (lastSeq == null) ? 1L : lastSeq + 1;
    }

    private boolean shouldNotifyKitchen(OrderStatus status) {
        return status == OrderStatus.CONFIRMED || status == OrderStatus.PREPARATION_STARTED;
    }

    public void notifyKitchen(Order order) {
        String queueName = "queue.merchant." + order.getMerchant().getId();
        rabbitQueueManager.createMerchantQueue(order.getMerchant().getId().toString());
        rabbitTemplate.convertAndSend(queueName, new FullOrderDto(order).toComandString());
        System.err.println("\n\n\n\n OLOKINHO");

    }


    private Merchant getOpenedMerchant(UUID merchantId) {
        var merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new EntityNotFoundException("Merchant não encontrado."));

        boolean openedRecently = merchant.getLastOpenedUtc() != null
                && Instant.now().minusSeconds(30000).isBefore(merchant.getLastOpenedUtc());

        if (!openedRecently)
            throw new ValidationException("O restaurante não está aberto no momento.");

        return merchant;
    }
}
