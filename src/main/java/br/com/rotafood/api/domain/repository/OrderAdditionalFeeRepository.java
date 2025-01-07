package br.com.rotafood.api.domain.repository;

import br.com.rotafood.api.domain.entity.order.OrderAdditionalFee;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderAdditionalFeeRepository extends JpaRepository<OrderAdditionalFee, UUID> {
    List<OrderAdditionalFee> findAllByMerchantId(UUID merchantId);
    Optional<OrderAdditionalFee> findByIdAndMerchantId(UUID id, UUID merchantId);
    List<OrderAdditionalFee> findAllByOrderId(UUID orderId);
}
