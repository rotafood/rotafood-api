package br.com.rotafood.api.application.dto.catalog;

import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.ItemOptionGroup;

public record ItemOptionGroupDto(
    UUID id,
    Integer min,
    Integer max,
    Integer index,
    OptionGroupDto optionGroup
) {
    public ItemOptionGroupDto(ItemOptionGroup itemOptionGroup) {
        this(
            itemOptionGroup.getId(),
            itemOptionGroup.getMin(),
            itemOptionGroup.getMax(),
            itemOptionGroup.getIndex(),
            new OptionGroupDto(itemOptionGroup.getOptionGroup())
        );
    }
}