package br.com.rotafood.api.application.dto.catalog;

import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.CatalogContext;
import br.com.rotafood.api.domain.entity.catalog.ContextModifier;

public record ContextModifierDto(
    UUID id,
    PriceDto price,
    CatalogContext catalogContext
) {
    public ContextModifierDto(ContextModifier contextModifier) {
        this(
            contextModifier.getId(),
            contextModifier.getPrice() != null ? new PriceDto(contextModifier.getPrice()) : null,
            contextModifier.getCatalogContext()
        );
    }
}
