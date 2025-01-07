package br.com.rotafood.api.application.service.order;

import br.com.rotafood.api.application.dto.order.OrderItemDto;
import br.com.rotafood.api.domain.entity.catalog.Item;
import br.com.rotafood.api.domain.entity.order.Order;
import br.com.rotafood.api.domain.entity.order.OrderItem;
import br.com.rotafood.api.domain.repository.ItemRepository;
import br.com.rotafood.api.domain.repository.OrderItemRepository;
import br.com.rotafood.api.domain.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderItemService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Transactional
    public OrderItem createOrUpdate(OrderItemDto dto, UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));

        Item catalogItem = itemRepository.findById(dto.item().id())
                .orElseThrow(() -> new EntityNotFoundException("Catalog item not found."));

        OrderItem orderItem = dto.id() != null
                ? orderItemRepository.findById(dto.id())
                        .orElseThrow(() -> new EntityNotFoundException("OrderItem not found."))
                : new OrderItem();

        orderItem.setItem(catalogItem);
        orderItem.setQuantity(dto.quantity());
        orderItem.setTotalPrice(dto.totalPrice());
        orderItem.setOrder(order);

        order.addItem(orderItem);

        orderRepository.save(order);
        return orderItemRepository.save(orderItem);
    }

    @Transactional
    public void deleteById(UUID itemId) {
        OrderItem orderItem = orderItemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("OrderItem not found."));
        Order order = orderItem.getOrder();
        order.removeItem(orderItem);
        orderRepository.save(order);
        orderItemRepository.delete(orderItem);
    }

    public List<OrderItem> getByOrderId(UUID orderId) {
        return orderItemRepository.findAllByOrderId(orderId);
    }
}
