package br.com.rotafood.api.domain.merchant.repositories;

import java.util.UUID;
import br.com.rotafood.api.domain.merchant.models.MerchantUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantUserRepository extends JpaRepository<MerchantUser, UUID> {

    boolean existsByEmail(String emailAddress);


}