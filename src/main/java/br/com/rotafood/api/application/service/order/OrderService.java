package br.com.rotafood.api.application.service.order;


import br.com.rotafood.api.application.dto.order.FullOrderDto;
import br.com.rotafood.api.domain.entity.order.Order;
import br.com.rotafood.api.domain.entity.order.OrderStatus;

import br.com.rotafood.api.domain.entity.order.OrderType;
import br.com.rotafood.api.domain.repository.OrderRepository;
import br.com.rotafood.api.infra.rabbitmq.RabbitQueueManager;
import br.com.rotafood.api.infra.twilio.TwilioService;
import br.com.rotafood.api.infra.utils.DateUtils;
import br.com.rotafood.api.domain.repository.MerchantRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private OrderTotalService orderTotalService;

    @Autowired
    private OrderCustomerService orderCustomerService;

    @Autowired
    private OrderScheduleService orderScheduleService;

    @Autowired
    private OrderDeliveryService orderDeliveryService;

    @Autowired
    private OrderTakeoutService orderTakeoutService;

    @Autowired
    private OrderPaymentService orderPaymentService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderAdditionalFeeService orderAdditionalFeeService;

    @Autowired
    private OrderBenefitService orderBenefitService;

    @Autowired
    private TwilioService twilioService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitQueueManager rabbitQueueManager;


    @Value("${api.security.allowed.origin}")
    private String allowedOrigin;

    private static final String ADMIN_PHONE_NUMBER = "+5519981859845";



    public List<Order> getAllByMerchantId(UUID merchantId) {
        return orderRepository.findAllByMerchantId(merchantId);
    }

    public Order getByIdAndMerchantId(UUID orderId, UUID merchantId) {
        return orderRepository.findByIdAndMerchantId(orderId, merchantId)
                .orElseThrow(() -> new EntityNotFoundException("Order não encontrado."));
    }

    @Transactional
    public Order createOrUpdate(FullOrderDto fullOrderDto, UUID merchantId) {
        
        var merchant = merchantRepository.findById(merchantId)
            .orElseThrow(() -> new EntityNotFoundException("Merchant não encontrado."));

        if (merchant.getLastOpenedUtc() == null || 
            Instant.now().minusSeconds(30000).isAfter(merchant.getLastOpenedUtc())) {
            throw new ValidationException("O restaurante não está aberto no momento.");
        }

        

        Order order = fullOrderDto.id() != null
            ? orderRepository.findByIdAndMerchantId(fullOrderDto.id(), merchantId)
                .orElseThrow(() -> new EntityNotFoundException("Order não encontrado."))
                    : new Order();
                    
        if (order.getMerchantSequence() == null) {
            Long maxSequence = orderRepository.findMaxMerchantSequenceByMerchantId(merchantId);
            Long nextSequence = (maxSequence == null) ? 1L : maxSequence + 1L;
            order.setMerchantSequence(nextSequence);
        }

        order.setPreparationStartDateTime(
            fullOrderDto.preparationStartDateTime() != null ? 
            fullOrderDto.preparationStartDateTime().toInstant() : Instant.now()
        );
        order.setSalesChannel(fullOrderDto.salesChannel());
        order.setTiming(fullOrderDto.timing());
        order.setType(fullOrderDto.type());
        order.setStatus(fullOrderDto.status());
        order.setExtraInfo(fullOrderDto.extraInfo());

        order.setMerchant(merchant);

        order.setTotal(fullOrderDto.total() != null ? this.orderTotalService.createOrUpdate(fullOrderDto.total()) : null);

        order.setCustomer(fullOrderDto.customer() != null ? this.orderCustomerService.createOrUpdate(fullOrderDto.customer()) : null);

        order.setDelivery(fullOrderDto.delivery() != null ? this.orderDeliveryService.createOrUpdate(fullOrderDto.delivery()) : null);

        order.setSchedule(fullOrderDto.schedule() != null ? this.orderScheduleService.createOrUpdate(fullOrderDto.schedule()) : null);

        order.setTakeout(fullOrderDto.takeout() != null ? this.orderTakeoutService.createOrUpdate(fullOrderDto.takeout()) : null);

        order.setCustomer(fullOrderDto.customer() != null ? this.orderCustomerService.createOrUpdate(fullOrderDto.customer()) : null);

        order.setPayment(fullOrderDto.payment() != null ? this.orderPaymentService.createOrUpdate(fullOrderDto.payment()) : null);

        orderRepository.save(order);

        orderItemService.synchronizeItems(fullOrderDto.items(), order);

        if (fullOrderDto.additionalFees() != null) {
            orderAdditionalFeeService.synchronizeAdditionalFees(fullOrderDto.additionalFees(), order);
        }

        if (fullOrderDto.benefits() != null) {
            orderBenefitService.synchronizeBenefits(fullOrderDto.benefits(), order);
        }

        this.sendSmsNotification(order);

        return order;
    }

    @Transactional
    public void updateOrderStatus(UUID merchantId, UUID orderId, OrderStatus status) {
    
        Order order = this.orderRepository.findByIdAndMerchantId(orderId, merchantId)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));
    
        order.setStatus(status);
        orderRepository.save(order);
    
        if (status == OrderStatus.CONFIRMED || status == OrderStatus.PREPARATION_STARTED) {
            FullOrderDto fullOrderDto = new FullOrderDto(order);
            String message = fullOrderDto.toComandString();
    
            String queueName = "queue.merchant." + merchantId;
    
            rabbitQueueManager.createMerchantQueue(merchantId.toString());
    
            rabbitTemplate.convertAndSend(queueName, message);
        }
    }
    


    @Transactional
    public void deleteByIdAndMerchantId(UUID orderId, UUID merchantId) {
        Order order = orderRepository.findByIdAndMerchantId(orderId, merchantId)
                .orElseThrow(() -> new EntityNotFoundException("Order não encontrado."));
        orderRepository.delete(order);
    }

    public Page<Order> getAllByFilters(
        UUID merchantId, 
        List<OrderType> orderTypes, 
        List<OrderStatus> orderStatus,
        String startDate, 
        String endDate,
        Pageable pageable
    ) {
        Instant start = DateUtils.parseDateStringToInstant(startDate, false);
        Instant end = DateUtils.parseDateStringToInstant(endDate, true);

        return orderRepository.findAllByFilters(merchantId, orderTypes, orderStatus, start, end, pageable);
    }

    public Page<Order> polling(
        UUID merchantId, 
        List<OrderType> orderTypes, 
        List<OrderStatus> orderStatus,
        String startDate, 
        String endDate,
        Pageable pageable
    ) {
        Instant start = DateUtils.parseDateStringToInstant(startDate, false);
        Instant end = DateUtils.parseDateStringToInstant(endDate, true);


        this.merchantRepository.updateLastOpenedUtc(merchantId, Instant.now());

        return orderRepository.findAllByFilters(merchantId, orderTypes, orderStatus, start, end, pageable);
    }

    private void sendSmsNotification(Order order) {
        String orderLink = String.format("%s/cardapios/%s/pedidos/%s", 
            allowedOrigin, 
            order.getMerchant().getOnlineName(), 
            order.getId()
        );

        String message = String.format(
            "🛒 *Novo Pedido Criado!*\n\n" +
            "📌 *ID:* %s\n" +
            "📍 *Canal de Venda:* %s\n" +
            "⌚ *Criado em:* %s\n" +
            "💰 *Total:* R$ %.2f\n\n" +
            "🔗 *Veja mais detalhes:* %s",
            order.getId(),
            order.getSalesChannel(),
            DateUtils.formatToBrazilianTime(order.getCreatedAt()),
            order.getTotal().getOrderAmount(),
            orderLink
        );

        twilioService.sendSms(ADMIN_PHONE_NUMBER, message);
    }

}

