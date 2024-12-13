package br.com.rotafood.api.application.dto.catalog;

import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.ProductOptionGroup;

public record ProductOptionGroupDto(
    UUID id,
    Integer min,
    Integer max,
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