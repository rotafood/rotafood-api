package br.com.rotafood.api.domain.repository;

import br.com.rotafood.api.domain.entity.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
    List<OrderItem> findAllByOrderId(UUID findByOrderId);
    
}
