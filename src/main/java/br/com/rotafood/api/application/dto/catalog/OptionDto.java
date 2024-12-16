package br.com.rotafood.api.application.dto.catalog;

import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.Option;
import br.com.rotafood.api.domain.entity.catalog.Status;

public record OptionDto (
    UUID id,
    Status status,
    Integer index,
    List<ContextModifierDto> contextModifiers,
    List<Integer> fractions,
    ProductOptionDto product
) {
    public OptionDto(Option option) {
        this(
            option.getId(),
            option.getStatus(),
            option.getIndex(),
            option.getContextModifiers() != null ? option.getContextModifiers().stream().map(ContextModifierDto::new).toList() : null,
            option.getFractions() != null ? option.getFractions() : null,
            option.getProduct() != null ? new ProductOptionDto(option.getProduct()) : null
        );
    }
}
