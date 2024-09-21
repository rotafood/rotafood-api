package br.com.rotafood.api.application.dto.catalog;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.CatalogContext;
import br.com.rotafood.api.domain.entity.catalog.Category;

public record CatalogDto(
    UUID id,
    Date modifiedAt,
    List<CatalogContext> catalogContexts,
    List<Category> catalogCategories
) {}
