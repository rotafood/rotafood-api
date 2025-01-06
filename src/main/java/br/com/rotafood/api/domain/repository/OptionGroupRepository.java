package br.com.rotafood.api.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotafood.api.domain.entity.catalog.OptionGroup;
import br.com.rotafood.api.domain.entity.catalog.OptionGroupType;

public interface OptionGroupRepository extends JpaRepository<OptionGroup, UUID> {

    List<OptionGroup> findAllByMerchantId(UUID merchantId);

    OptionGroup findByIdAndMerchantId(UUID id, UUID merchantId);

    List<OptionGroup> findAllByMerchantIdAndOptionGroupType(UUID merchantId, OptionGroupType optionGroupType);


}
