package br.com.rotafood.api.merchant.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotafood.api.catalog.domain.entity.OptionGroup;
import br.com.rotafood.api.catalog.domain.entity.OptionGroupType;

public interface OptionGroupRepository extends JpaRepository<OptionGroup, UUID> {

    List<OptionGroup> findAllByMerchantId(UUID merchantId);

    Optional<OptionGroup> findByIdAndMerchantId(UUID id, UUID merchantId);

    List<OptionGroup> findAllByMerchantIdAndOptionGroupType(UUID merchantId, OptionGroupType optionGroupType);


}
