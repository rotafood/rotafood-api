package br.com.rotafood.api.application.service.order;


import br.com.rotafood.api.application.dto.order.FullOrderDto;
import br.com.rotafood.api.application.dto.order.OrderAdditionalFeeDto;
import br.com.rotafood.api.application.dto.order.OrderBenefitDto;
import br.com.rotafood.api.application.dto.order.OrderItemDto;
import br.com.rotafood.api.domain.entity.order.Order;
import br.com.rotafood.api.domain.entity.order.OrderStatus;
import br.com.rotafood.api.domain.entity.order.OrderType;
import br.com.rotafood.api.domain.repository.OrderRepository;
import br.com.rotafood.api.infra.utils.DateUtils;
import br.com.rotafood.api.domain.repository.MerchantRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
    private OrderDeliveryService orderDeliveryService;

    @Autowired
    private OrderCustomerService orderCustomerService;


    @Autowired
    private OrderTakeoutService orderTakeOrderTakeoutService;

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
        Order order = fullOrderDto.id() != null
                ? orderRepository.findByIdAndMerchantId(fullOrderDto.id(), merchantId)
                        .orElseThrow(() -> new EntityNotFoundException("Order not found."))
                : new Order();

        var merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new EntityNotFoundException("Merchant not found."));

        order.setMerchant(merchant);
        order.setType(fullOrderDto.type());
        order.setSalesChannel(fullOrderDto.salesChannel());
        order.setTiming(fullOrderDto.timing());
        order.setStatus(fullOrderDto.status());
        order.setExtraInfo(fullOrderDto.extraInfo());
        order.setPreparationStartDateTime(fullOrderDto.preparationStartDateTime().toInstant());

        order = orderRepository.save(order);

        if (fullOrderDto.total() != null) {
            orderTotalService.createOrUpdate(fullOrderDto.total(), order.getId());
        }

        if (fullOrderDto.takeout() != null) {
            orderTakeOrderTakeoutService.createOrUpdate(fullOrderDto.takeout(), order.getId());
        }

        if (fullOrderDto.customer() != null) {
            orderCustomerService.createOrAssociate(fullOrderDto.customer(), order.getId());
        }

        if (fullOrderDto.delivery() != null) {
            orderDeliveryService.createOrUpdate(fullOrderDto.delivery(), order.getId());
        }

        if (fullOrderDto.indoor() != null) {
            orderIndoorService.createOrUpdate(fullOrderDto.indoor(), order.getId());
        }

        if (fullOrderDto.payment() != null) {
            orderPaymentService.createOrUpdate(fullOrderDto.payment(), order.getId());
        }

        if (fullOrderDto.items() != null) {
            for (OrderItemDto itemDto : fullOrderDto.items()) {
                orderItemService.createOrUpdate(itemDto, order.getId());
            }
        }

        if (fullOrderDto.benefits() != null) {
            for (OrderBenefitDto benefitDto : fullOrderDto.benefits()) {
                orderBenefitService.createOrUpdate(benefitDto, order.getId());
            }
        }

        if (fullOrderDto.additionalFees() != null) {
            for (OrderAdditionalFeeDto feeDto : fullOrderDto.additionalFees()) {
                orderAdditionalFeeService.createOrUpdate(feeDto, order.getId());
            }
        }

        return orderRepository.save(order);
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


}

