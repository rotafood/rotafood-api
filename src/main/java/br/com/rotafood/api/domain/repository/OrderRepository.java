package br.com.rotafood.api.domain.repository;

import br.com.rotafood.api.domain.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findAllByMerchantId(UUID merchantId);
    Optional<Order> findByIdAndMerchantId(UUID id, UUID merchantId);
}
