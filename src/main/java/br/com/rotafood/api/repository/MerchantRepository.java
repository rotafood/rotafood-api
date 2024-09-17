package br.com.rotafood.api.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotafood.api.domain.merchant.Merchant;

import java.util.UUID;

public interface MerchantRepository extends JpaRepository<Merchant, UUID> {}
