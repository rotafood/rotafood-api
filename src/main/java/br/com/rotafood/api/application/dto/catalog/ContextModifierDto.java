package br.com.rotafood.api.application.dto.catalog;

import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.CatalogContext;
import br.com.rotafood.api.domain.entity.catalog.ContextModifier;
import br.com.rotafood.api.domain.entity.catalog.Status;

public record ContextModifierDto(
    UUID id,
    Status status,
    PriceDto price,
    CatalogContext catalogContext
) {
    public ContextModifierDto(ContextModifier contextModifier) {
        this(
            contextModifier.getId(),
            contextModifier.getStatus(),
            contextModifier.getPrice() != null ? new PriceDto(contextModifier.getPrice()) : null,
            contextModifier.getCatalogContext()
        );
    }
}
