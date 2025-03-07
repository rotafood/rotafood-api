package br.com.rotafood.api.application.service.order;

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
    public OrderIndoor createOrUpdate(OrderIndoorDto dto) {
        OrderIndoor indoor = dto.id() != null ? this.orderIndoorRepository.findById(dto.id()).orElseThrow(() -> 
        new EntityNotFoundException("Indoor não encontrado para ID: " + dto.id())) : new OrderIndoor();
        indoor.setMode(dto.mode());
        indoor.setDeliveryDateTime(dto.deliveryDateTime());

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
