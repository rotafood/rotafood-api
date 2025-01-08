package br.com.rotafood.api.domain.repository;

import br.com.rotafood.api.domain.entity.order.TransactionInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionInformationRepository extends JpaRepository<TransactionInformation, UUID> {
}
