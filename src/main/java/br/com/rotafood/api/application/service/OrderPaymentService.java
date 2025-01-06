package br.com.rotafood.api.application.service;

import br.com.rotafood.api.application.dto.order.OrderPaymentDto;
import br.com.rotafood.api.domain.entity.order.Order;
import br.com.rotafood.api.domain.entity.order.OrderPayment;
import br.com.rotafood.api.domain.repository.OrderPaymentRepository;
import br.com.rotafood.api.domain.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderPaymentService {

    @Autowired
    private OrderPaymentRepository orderPaymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public OrderPayment createOrUpdate(OrderPaymentDto dto, UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));

        OrderPayment payment = order.getPayment() != null
                ? order.getPayment()
                : new OrderPayment();

        payment.setDescription(dto.description());
        payment.setPending(dto.pending());
        payment.setPrepaid(dto.prepaid());

        order.setPayment(payment);

        orderRepository.save(order);
        return orderPaymentRepository.save(payment);
    }

    @Transactional
    public void deleteByOrderId(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));

        if (order.getPayment() != null) {
            orderPaymentRepository.delete(order.getPayment());
            order.setPayment(null);
            orderRepository.save(order);
        }
    }

    public OrderPayment getByOrderId(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));
        return order.getPayment();
    }
}
