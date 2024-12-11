package br.com.rotafood.api.application.dto.catalog;

import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.Weight;
import br.com.rotafood.api.domain.entity.catalog.WeightUnit;

public record WeightDto(
    UUID id,
    Integer quantity,
    WeightUnit unit
) {
    public WeightDto(Weight weight) {
        this(
            weight.getId(),
            weight.getQuantity(),
            weight.getUnit() != null ? weight.getUnit() : null
        );
    }
}
