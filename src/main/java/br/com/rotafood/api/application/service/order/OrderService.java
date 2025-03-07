package br.com.rotafood.api.application.service.order;


import br.com.rotafood.api.application.dto.order.FullOrderDto;
import br.com.rotafood.api.application.dto.order.OrderAdditionalFeeDto;
import br.com.rotafood.api.application.dto.order.OrderBenefitDto;
import br.com.rotafood.api.application.dto.order.OrderItemDto;
import br.com.rotafood.api.domain.entity.order.Order;
import br.com.rotafood.api.domain.entity.order.OrderAdditionalFee;
import br.com.rotafood.api.domain.entity.order.OrderBenefit;
import br.com.rotafood.api.domain.entity.order.OrderCustomer;
import br.com.rotafood.api.domain.entity.order.OrderDelivery;
import br.com.rotafood.api.domain.entity.order.OrderIndoor;
import br.com.rotafood.api.domain.entity.order.OrderItem;
import br.com.rotafood.api.domain.entity.order.OrderPayment;
import br.com.rotafood.api.domain.entity.order.OrderSalesChannel;
import br.com.rotafood.api.domain.entity.order.OrderSchedule;
import br.com.rotafood.api.domain.entity.order.OrderStatus;
import br.com.rotafood.api.domain.entity.order.OrderTakeout;
import br.com.rotafood.api.domain.entity.order.OrderTiming;
import br.com.rotafood.api.domain.entity.order.OrderTotal;
import br.com.rotafood.api.domain.entity.order.OrderType;
import br.com.rotafood.api.domain.repository.OrderRepository;
import br.com.rotafood.api.domain.repository.OrderTotalRepository;
import br.com.rotafood.api.infra.utils.DateUtils;
import br.com.rotafood.api.domain.repository.MerchantRepository;
import br.com.rotafood.api.domain.repository.OrderPaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderTotalRepository orderTotalRepository;

    @Autowired
    private OrderPaymentRepository orderPaymentRepository;

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
    private OrderIndoorService orderIndoorService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderBenefitService orderBenefitService;


    @Autowired
    private OrderAdditionalFeeService orderAdditionalFeeService;

    public List<Order> getAllByMerchantId(UUID merchantId) {
        return orderRepository.findAllByMerchantId(merchantId);
    }

    public Order getByIdAndMerchantId(UUID orderId, UUID merchantId) {
        return orderRepository.findByIdAndMerchantId(orderId, merchantId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));
    }

    @Transactional
    public Order createOrUpdate(FullOrderDto fullOrderDto, UUID merchantId) {
        var merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new EntityNotFoundException("Merchant not found."));

        Order order = fullOrderDto.id() != null
                ? orderRepository.findByIdAndMerchantId(fullOrderDto.id(), merchantId)
                        .orElseThrow(() -> new EntityNotFoundException("Order not found."))
                : new Order();


        order.setPreparationStartDateTime(fullOrderDto.preparationStartDateTime().toInstant());
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

        order.setIndoor(fullOrderDto.indoor() != null ? this.orderIndoorService.createOrUpdate(fullOrderDto.indoor()) : null);


        order.setPayment(fullOrderDto.payment() != null ? this.orderPaymentService.createOrUpdate(fullOrderDto.payment()) : null);

        orderRepository.save(order);

        if (fullOrderDto.items() != null) {
            if (order.getItems() != null) {
                order.getItems().clear();
            }
            fullOrderDto.items().forEach(item -> {
                this.orderItemService.createOrUpdate(item, order);
            });
            
        }

        return order;
    }

    @Transactional
    public void deleteByIdAndMerchantId(UUID orderId, UUID merchantId) {
        Order order = orderRepository.findByIdAndMerchantId(orderId, merchantId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));
        orderRepository.delete(order);
    }

    public Page<Order> getAllByFilters(
        UUID merchantId, 
        List<OrderType> orderTypes, 
        List<OrderStatus> orderStatuses,
        String startDate, 
        String endDate,
        Pageable pageable
    ) {
        Instant start = DateUtils.parseDateStringToInstant(startDate, false);
        Instant end = DateUtils.parseDateStringToInstant(endDate, true);

        return orderRepository.findAllByFilters(merchantId, orderTypes, orderStatuses, start, end, pageable);
    }


    @Transactional
    public Order createTestOrder(UUID merchantId) {
        var merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new EntityNotFoundException("Merchant not found."));


        OrderTotal total = new OrderTotal();
        total.setBenefits(BigDecimal.valueOf(5.00));
        total.setDeliveryFee(BigDecimal.valueOf(10.00));
        total.setOrderAmount(BigDecimal.valueOf(50.00));
        total.setSubTotal(BigDecimal.valueOf(40.00));
        total.setAdditionalFees(BigDecimal.valueOf(0.00));
        this.orderTotalRepository.save(total);

        OrderPayment payment = new OrderPayment();
        payment.setDescription("Pagamento em dinheiro.");
        payment.setPending(BigDecimal.ZERO);
        payment.setPrepaid(BigDecimal.valueOf(50.00));

        this.orderPaymentRepository.save(payment);

        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setMerchant(merchant);
        order.setCreatedAt(Instant.now());
        order.setModifiedAt(Instant.now());
        order.setPreparationStartDateTime(Instant.now());
        order.setType(OrderType.DELIVERY);
        order.setStatus(OrderStatus.CREATED);
        order.setSalesChannel(OrderSalesChannel.ROTAFOOD);
        order.setTiming(OrderTiming.IMMEDIATE);
        order.setExtraInfo("Pedido de teste gerado automaticamente.");
        order.setTotal(total);
        order.setPayment(payment);

        return orderRepository.save(order);
    }

}

