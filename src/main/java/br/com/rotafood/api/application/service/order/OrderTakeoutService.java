package br.com.rotafood.api.application.service.order;

import br.com.rotafood.api.application.dto.order.OrderTakeoutDto;
import br.com.rotafood.api.domain.entity.order.Order;
import br.com.rotafood.api.domain.entity.order.OrderTakeout;
import br.com.rotafood.api.domain.repository.OrderRepository;
import br.com.rotafood.api.domain.repository.OrderTakeoutRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderTakeoutService {

    @Autowired
    private OrderTakeoutRepository orderTakeoutRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public OrderTakeout createOrUpdate(OrderTakeoutDto dto, UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));

        OrderTakeout takeout = order.getTakeout() != null
                ? order.getTakeout()
                : new OrderTakeout();

        takeout.setTakeoutDateTime(dto.takeoutDateTime());
        takeout.setComments(dto.comments());

        order.setTakeout(takeout);

        orderRepository.save(order);
        return orderTakeoutRepository.save(takeout);
    }

    @Transactional
    public void deleteByOrderId(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));

        if (order.getTakeout() != null) {
            orderTakeoutRepository.delete(order.getTakeout());
            order.setTakeout(null);
            orderRepository.save(order);
        }
    }

    public OrderTakeout getByOrderId(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));
        return order.getTakeout();
    }
}
