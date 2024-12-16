package br.com.rotafood.api.application.dto.catalog;

import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.DietaryRestrictions;
import br.com.rotafood.api.domain.entity.catalog.Product;
import br.com.rotafood.api.domain.entity.catalog.Serving;

public record ProductOptionDto(
    UUID id,
    String name,
    String description,
    String ean,
    UUID optionId,
    Serving serving,
    String imagePath
) {
    public ProductOptionDto(Product product) {
        this(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getEan(),
            product.getOption() != null ? product.getOption().getId() : null,
            product.getServing(),
            product.getImagePath()
        );
    }
}
