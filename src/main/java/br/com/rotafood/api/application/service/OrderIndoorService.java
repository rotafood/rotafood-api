package br.com.rotafood.api.application.service;

import br.com.rotafood.api.application.dto.order.OrderIndoorDto;
import br.com.rotafood.api.domain.entity.order.Order;
import br.com.rotafood.api.domain.entity.order.OrderIndoor;
import br.com.rotafood.api.domain.repository.OrderRepository;
import br.com.rotafood.api.domain.repository.OrderIndoorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderIndoorService {

    @Autowired
    private OrderIndoorRepository orderIndoorRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public OrderIndoor createOrUpdate(OrderIndoorDto dto, UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));

        OrderIndoor indoor = order.getIndoor() != null
                ? order.getIndoor()
                : new OrderIndoor();

        indoor.setMode(dto.mode());
        indoor.setDeliveryDateTime(dto.deliveryDateTime());

        order.setIndoor(indoor);

        orderRepository.save(order);
        return orderIndoorRepository.save(indoor);
    }

    @Transactional
    public void deleteByOrderId(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));

        if (order.getIndoor() != null) {
            orderIndoorRepository.delete(order.getIndoor());
            order.setIndoor(null);
            orderRepository.save(order);
        }
    }

    public OrderIndoor getByOrderId(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));
        return order.getIndoor();
    }
}
