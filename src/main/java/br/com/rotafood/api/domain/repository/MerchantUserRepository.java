package br.com.rotafood.api.domain.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotafood.api.domain.entity.merchant.MerchantUser;

public interface MerchantUserRepository extends JpaRepository<MerchantUser, UUID> {

    boolean existsByEmail(String email);

    MerchantUser findByEmail(String email);


}