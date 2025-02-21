package br.com.rotafood.api.application.dto.catalog;

import java.util.Date;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.Catalog;
import br.com.rotafood.api.domain.entity.catalog.CatalogContext;
import br.com.rotafood.api.domain.entity.catalog.AvailabilityStatus;

public record CatalogDto(
    UUID id,
    Date modifiedAt,
    AvailabilityStatus status,
    CatalogContext catalogContext,
    UUID iFoodCatalofId
) {
    public CatalogDto(Catalog catalog) {

        this(
            catalog.getId(), 
            catalog.getModifiedAt(), 
            catalog.getStatus(), 
            catalog.getCatalogContext(),
            catalog.getIFoodCatalogId()
            );
    }
}
