package br.com.rotafood.api.application.service;

import br.com.rotafood.api.application.dto.order.OrderTotalDto;
import br.com.rotafood.api.domain.entity.order.Order;
import br.com.rotafood.api.domain.entity.order.OrderTotal;
import br.com.rotafood.api.domain.repository.OrderRepository;
import br.com.rotafood.api.domain.repository.OrderTotalRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderTotalService {

    @Autowired
    private OrderTotalRepository orderTotalRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public OrderTotal createOrUpdate(OrderTotalDto orderTotalDto, UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));

        OrderTotal orderTotal = order.getTotal() != null
                ? order.getTotal()
                : new OrderTotal();

        orderTotal.setBenefits(orderTotalDto.benefits());
        orderTotal.setDeliveryFee(orderTotalDto.deliveryFee());
        orderTotal.setOrderAmount(orderTotalDto.orderAmount());
        orderTotal.setSubTotal(orderTotalDto.subTotal());
        orderTotal.setAdditionalFees(orderTotalDto.additionalFees());

        order.setTotal(orderTotal);
        orderTotal.setOrder(order);

        orderRepository.save(order);
        return orderTotalRepository.save(orderTotal);
    }

    @Transactional
    public void deleteByOrderId(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));

        if (order.getTotal() != null) {
            orderTotalRepository.delete(order.getTotal());
            order.setTotal(null); 
            orderRepository.save(order); 
        }
    }

    public OrderTotal getByOrderId(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));
        return order.getTotal();
    }
}
