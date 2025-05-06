package br.com.rotafood.api.merchant.domain.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotafood.api.merchant.domain.entity.MerchantOrderEstimate;



public interface MerchantOrderEstimateRepository extends JpaRepository<MerchantOrderEstimate, UUID> {


}
 