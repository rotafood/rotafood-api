package br.com.rotafood.api.domain.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.rotafood.api.domain.entity.logistic.LogisticSetting;

public interface LogisticSettingRepository extends JpaRepository<LogisticSetting, UUID> {

    Optional<LogisticSetting> findByIdAndMerchantId(UUID id, UUID merchantId);

    Optional<LogisticSetting> findByMerchantId(UUID merchantId);



}
 