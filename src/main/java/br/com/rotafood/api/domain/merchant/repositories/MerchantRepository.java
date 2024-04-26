package br.com.rotafood.api.domain.merchant.repositories;
import br.com.rotafood.api.domain.merchant.models.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface MerchantRepository extends JpaRepository<Merchant, UUID> {}
