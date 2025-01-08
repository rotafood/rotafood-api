package br.com.rotafood.api.domain.repository;

import br.com.rotafood.api.domain.entity.order.CashInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CashInformationRepository extends JpaRepository<CashInformation, UUID> {
}
