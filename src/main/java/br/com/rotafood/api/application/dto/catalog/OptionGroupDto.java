package br.com.rotafood.api.application.dto.catalog;

import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.OptionGroup;
import br.com.rotafood.api.domain.entity.catalog.OptionGroupType;
import br.com.rotafood.api.domain.entity.catalog.AvailabilityStatus;

public record OptionGroupDto(
    UUID id,
    String name,
    AvailabilityStatus status,
    OptionGroupType optionGroupType,
    List<OptionDto> options,
    UUID iFoodOptionGroupId
) {
    public OptionGroupDto(OptionGroup optionGroup) {
        this(
            optionGroup.getId(),
            optionGroup.getName(),
            optionGroup.getStatus(),
            optionGroup.getOptionGroupType(),
            optionGroup.getOptions().stream().map(OptionDto::new).toList(),
            optionGroup.getIFoodOptionGroupId()
        );
    }
}
