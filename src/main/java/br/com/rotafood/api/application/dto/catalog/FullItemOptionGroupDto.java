package br.com.rotafood.api.application.dto.catalog;

import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.ItemOptionGroup;
import jakarta.validation.constraints.NotNull;

public record FullItemOptionGroupDto(
    UUID id,
    @NotNull
    Integer min,
    @NotNull
    Integer max,
    @NotNull
    Integer index,
    OptionGroupDto optionGroup
) {
    public FullItemOptionGroupDto(ItemOptionGroup itemOptionGroup) {
        this(
            itemOptionGroup.getId(),
            itemOptionGroup.getMin(),
            itemOptionGroup.getMax(),
            itemOptionGroup.getIndex(),
            itemOptionGroup.getOptionGroup() != null ? new OptionGroupDto(itemOptionGroup.getOptionGroup()) : null
        );
    }
}