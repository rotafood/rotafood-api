package br.com.rotafood.api.application.service.order;

import br.com.rotafood.api.application.dto.order.OrderDeliveryDto;
import br.com.rotafood.api.domain.entity.order.Order;
import br.com.rotafood.api.domain.entity.order.OrderDelivery;
import br.com.rotafood.api.domain.entity.address.Address;
import br.com.rotafood.api.domain.repository.OrderDeliveryRepository;
import br.com.rotafood.api.domain.repository.OrderRepository;
import br.com.rotafood.api.domain.repository.AddressRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderDeliveryService {

    @Autowired
    private OrderDeliveryRepository orderDeliveryRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Transactional
    public OrderDelivery createOrUpdate(OrderDeliveryDto orderDeliveryDto) {

         OrderDelivery orderDelivery = orderDeliveryDto.id() != null 
            ? this.orderDeliveryRepository.findById(orderDeliveryDto.id()).orElseThrow(() -> 
                new EntityNotFoundException("Delivery não encontrado para ID: " + orderDeliveryDto.id()))
            : new OrderDelivery();

        Address address = orderDeliveryDto.address() != null
                ? addressRepository.save(new Address(orderDeliveryDto.address()))
                : new Address(orderDeliveryDto.address());

        orderDelivery.setMode(orderDeliveryDto.mode());
        orderDelivery.setDeliveryBy(orderDeliveryDto.deliveryBy());
        orderDelivery.setDescription(orderDeliveryDto.description());
        orderDelivery.setPickupCode(orderDeliveryDto.pickupCode());
        orderDelivery.setDeliveryDateTime(orderDeliveryDto.deliveryDateTime());
        orderDelivery.setAddress(address);

        return orderDeliveryRepository.save(orderDelivery);
    }

    @Transactional
    public void deleteByOrderId(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));

        if (order.getDelivery() != null) {
            orderDeliveryRepository.delete(order.getDelivery());
            order.setDelivery(null);
            orderRepository.save(order);
        }
    }

    public OrderDelivery getByOrderId(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));
        return order.getDelivery();
    }
}
