package br.com.rotafood.api.application.dto.catalog;

import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.Category;
import br.com.rotafood.api.domain.entity.catalog.Status;

public record CategoryDto(
    UUID id,
    Integer index,
    String name,
    Status status,
    UUID iFoodCategoryId
) {
    public CategoryDto(Category category) {
        this(
            category.getId(),
            category.getIndex(),
            category.getName(),
            category.getStatus(),
            category.getIFoodCategoryId()
        );
    }
}
