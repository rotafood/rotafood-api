package br.com.rotafood.api.domain.repository;

import br.com.rotafood.api.domain.entity.order.OrderBenefit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderBenefitRepository extends JpaRepository<OrderBenefit, UUID> {
    List<OrderBenefit> findAllByOrderId(UUID orderId);

}
