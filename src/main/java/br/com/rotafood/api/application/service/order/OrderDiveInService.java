package br.com.rotafood.api.application.service.order;

import br.com.rotafood.api.application.dto.order.OrderDiveInDto;
import br.com.rotafood.api.domain.entity.order.Order;
import br.com.rotafood.api.domain.entity.order.OrderDiveIn;
import br.com.rotafood.api.domain.repository.OrderRepository;
import br.com.rotafood.api.domain.repository.OrderDiveInRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderDiveInService {

    @Autowired
    private OrderDiveInRepository orderDiveInRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public OrderDiveIn createOrUpdate(OrderDiveInDto dto) {
        OrderDiveIn indoor = dto.id() != null ? this.orderDiveInRepository.findById(dto.id()).orElseThrow(() -> 
        new EntityNotFoundException("DiveIn não encontrado para ID: " + dto.id())) : new OrderDiveIn();
        indoor.setDeliveryDateTime(dto.deliveryDateTime());

        return orderDiveInRepository.save(indoor);
    }

    @Transactional
    public void deleteByOrderId(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));

        if (order.getDiveIn() != null) {
            orderDiveInRepository.delete(order.getDiveIn());
            order.setDiveIn(null);
            orderRepository.save(order);
        }
    }

    public OrderDiveIn getByOrderId(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));
        return order.getDiveIn();
    }
}
