package br.com.rotafood.api.domain.repository;

import br.com.rotafood.api.domain.entity.order.OrderItemOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderItemOptionRepository extends JpaRepository<OrderItemOption, UUID> {
    List<OrderItemOption> findAllByMerchantId(UUID merchantId);
    Optional<OrderItemOption> findByIdAndMerchantId(UUID id, UUID merchantId);
}
