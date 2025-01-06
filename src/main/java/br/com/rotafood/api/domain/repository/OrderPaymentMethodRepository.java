package br.com.rotafood.api.domain.repository;

import br.com.rotafood.api.domain.entity.order.OrderPaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderPaymentMethodRepository extends JpaRepository<OrderPaymentMethod, UUID> {
    List<OrderPaymentMethod> findAllByMerchantId(UUID merchantId);
    Optional<OrderPaymentMethod> findByIdAndMerchantId(UUID id, UUID merchantId);
}
