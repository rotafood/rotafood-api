package br.com.rotafood.api.domain.repository;

import br.com.rotafood.api.domain.entity.order.OrderTotal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderTotalRepository extends JpaRepository<OrderTotal, UUID> {
    List<OrderTotal> findAllByMerchantId(UUID merchantId);
    Optional<OrderTotal> findByIdAndMerchantId(UUID id, UUID merchantId);
}
