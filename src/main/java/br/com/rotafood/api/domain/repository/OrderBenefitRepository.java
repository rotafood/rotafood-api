package br.com.rotafood.api.domain.repository;

import br.com.rotafood.api.domain.entity.order.OrderBenefit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderBenefitRepository extends JpaRepository<OrderBenefit, UUID> {
}
