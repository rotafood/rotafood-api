package br.com.rotafood.api.application.dto.catalog;

import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.Category;
import br.com.rotafood.api.domain.entity.catalog.Status;
import br.com.rotafood.api.domain.entity.catalog.TemplateType;

public record CategoryDto(
    UUID id,
    Integer index,
    String name,
    TemplateType template,
    Status status,
    UUID iFoodCategoryId
) {
    public CategoryDto(Category category) {
        this(
            category.getId(),
            category.getIndex(),
            category.getName(),
            category.getTemplate(),
            category.getStatus(),
            category.getIFoodCategoryId() 
        );
    }
}
