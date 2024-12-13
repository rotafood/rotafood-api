package br.com.rotafood.api.application.dto.catalog;

import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.CatalogContext;
import br.com.rotafood.api.domain.entity.catalog.ContextModifier;
import br.com.rotafood.api.domain.entity.catalog.Status;

public record ContextModifierDto(
    UUID id,
    UUID itemId,
    UUID optionId,
    UUID parentOptionId,
    Status status,
    PriceDto price,
    CatalogContext catalogContext
) {
    public ContextModifierDto(ContextModifier contextModifier) {
        this(
            contextModifier.getId(),
            contextModifier.getItem().getId(),
            contextModifier.getOption().getId(),
            contextModifier.getParentOptionModifier().getOption().getId(),
            contextModifier.getStatus(),
            new PriceDto(contextModifier.getPrice()),
            contextModifier.getCatalogContext()
        );
    }
}
