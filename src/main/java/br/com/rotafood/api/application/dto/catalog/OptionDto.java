package br.com.rotafood.api.application.dto.catalog;

import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.Option;
import jakarta.validation.constraints.NotNull;
import br.com.rotafood.api.domain.entity.catalog.AvailabilityStatus;

public record OptionDto (
    UUID id,
    @NotNull
    AvailabilityStatus status,
    @NotNull
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
