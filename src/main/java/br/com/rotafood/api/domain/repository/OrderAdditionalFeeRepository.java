package br.com.rotafood.api.domain.repository;

import br.com.rotafood.api.domain.entity.order.OrderAdditionalFee;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderAdditionalFeeRepository extends JpaRepository<OrderAdditionalFee, UUID> {
    List<OrderAdditionalFee> findAllByOrderId(UUID orderId);
}
