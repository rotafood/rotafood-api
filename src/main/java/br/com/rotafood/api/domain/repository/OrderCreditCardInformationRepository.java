package br.com.rotafood.api.domain.repository;

import br.com.rotafood.api.domain.entity.order.OrderCreditCardInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderCreditCardInformationRepository extends JpaRepository<OrderCreditCardInformation, UUID> {
}
