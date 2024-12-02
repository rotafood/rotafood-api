package br.com.rotafood.api.application.dto.catalog;

import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.Weight;

public record WeightDto(
    UUID id,
    Integer quantity,
    String weightUnit
) {
    public WeightDto(Weight weight) {
        this(
            weight.getId(),
            weight.getQuantity(),
            weight.getWeightUnit() != null ? weight.getWeightUnit().name() : null
        );
    }
}
