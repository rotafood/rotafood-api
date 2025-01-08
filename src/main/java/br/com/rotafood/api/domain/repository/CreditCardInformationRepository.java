package br.com.rotafood.api.domain.repository;

import br.com.rotafood.api.domain.entity.order.CreditCardInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CreditCardInformationRepository extends JpaRepository<CreditCardInformation, UUID> {
}
