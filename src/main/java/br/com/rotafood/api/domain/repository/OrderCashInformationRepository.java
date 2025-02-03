package br.com.rotafood.api.domain.repository;

import br.com.rotafood.api.domain.entity.order.OrderCashInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderCashInformationRepository extends JpaRepository<OrderCashInformation, UUID> {
}
