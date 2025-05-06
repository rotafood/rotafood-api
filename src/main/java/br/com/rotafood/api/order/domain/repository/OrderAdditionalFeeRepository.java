package br.com.rotafood.api.order.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotafood.api.order.domain.entity.OrderAdditionalFee;

import java.util.UUID;

public interface OrderAdditionalFeeRepository extends JpaRepository<OrderAdditionalFee, UUID> {
}
