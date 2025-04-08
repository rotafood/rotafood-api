package br.com.rotafood.api.domain.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotafood.api.domain.entity.logistic.MerchantLogisticSetting;

public interface MerchantLogisticSettingRepository extends JpaRepository<MerchantLogisticSetting, UUID> {

}
 