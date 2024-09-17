package br.com.rotafood.api.dto.catalog;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.domain.catalog.CatalogContext;
import br.com.rotafood.api.domain.catalog.Category;

public record CatalogDto(
    UUID id,
    Date modifiedAt,
    List<CatalogContext> catalogContexts,
    List<Category> catalogCategories
) {}
