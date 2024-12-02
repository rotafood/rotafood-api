package br.com.rotafood.api.application.dto.catalog;

import java.util.Date;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.CatalogContext;
import br.com.rotafood.api.domain.entity.catalog.Status;

public record GetCatalogDto(
    UUID id,
    Date modifiedAt,
    Status status,
    CatalogContext catalogContext
) {}
