package br.com.rotafood.api.application.service;


import br.com.rotafood.api.application.dto.order.FullOrderDto;
import br.com.rotafood.api.domain.entity.order.Order;
import br.com.rotafood.api.domain.repository.OrderRepository;
import br.com.rotafood.api.domain.repository.MerchantRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        order.setOrderType(fullOrderDto.orderType());
        order.setSalesChannel(fullOrderDto.salesChannel());
        order.setOrderTiming(fullOrderDto.orderTiming());
        order.setExtraInfo(fullOrderDto.extraInfo());
        order.setPreparationStartDateTime(fullOrderDto.preparationStartDateTime());
        order.setModifiedAt(fullOrderDto.modifiedAt());
        order.setCreatedAt(fullOrderDto.createdAt());

        order = orderRepository.save(order);

        if (fullOrderDto.total() != null) {
            orderTotalService.createOrUpdate(fullOrderDto.total(), order.getId());
        }

        if (fullOrderDto.customer() != null) {
            orderCustomerService.createOrAssociate(fullOrderDto.customer(), order.getId());
        }

        if (fullOrderDto.delivery() != null) {
            orderDeliveryService.createOrUpdate(fullOrderDto.delivery(), order.getId());
        }

        return orderRepository.save(order);
    }

    @Transactional
    public void deleteByIdAndMerchantId(UUID orderId, UUID merchantId) {
        Order order = orderRepository.findByIdAndMerchantId(orderId, merchantId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));
        orderRepository.delete(order);
    }
}

