package br.com.rotafood.api.application.dto.catalog;

import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.ProductOptionGroup;
import jakarta.validation.constraints.NotNull;

public record ProductOptionGroupDto(
    UUID id,
    @NotNull
    Integer min,
    @NotNull
    Integer max,
    @NotNull
    Integer index,
    OptionGroupDto optionGroup
) {
    public ProductOptionGroupDto(ProductOptionGroup productOptionGroup) {
        this(
            productOptionGroup.getId(),
            productOptionGroup.getMin(),
            productOptionGroup.getMax(),
            productOptionGroup.getIndex(),
            new OptionGroupDto(productOptionGroup.getOptionGroup())
        );
    }
}