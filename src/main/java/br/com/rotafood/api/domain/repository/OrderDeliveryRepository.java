package br.com.rotafood.api.domain.repository;

import br.com.rotafood.api.domain.entity.order.OrderDelivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderDeliveryRepository extends JpaRepository<OrderDelivery, UUID> {
    List<OrderDelivery> findAllByMerchantId(UUID merchantId);
    Optional<OrderDelivery> findByIdAndMerchantId(UUID id, UUID merchantId);
}
