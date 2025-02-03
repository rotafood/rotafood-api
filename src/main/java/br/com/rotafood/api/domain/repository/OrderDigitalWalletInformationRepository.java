package br.com.rotafood.api.domain.repository;

import br.com.rotafood.api.domain.entity.order.OrderDigitalWalletInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderDigitalWalletInformationRepository extends JpaRepository<OrderDigitalWalletInformation, UUID> {
}
