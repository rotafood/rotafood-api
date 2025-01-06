package br.com.rotafood.api.domain.repository;

import br.com.rotafood.api.domain.entity.order.OrderPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderPaymentRepository extends JpaRepository<OrderPayment, UUID> {
    List<OrderPayment> findAllByMerchantId(UUID merchantId);
    Optional<OrderPayment> findByIdAndMerchantId(UUID id, UUID merchantId);
}
