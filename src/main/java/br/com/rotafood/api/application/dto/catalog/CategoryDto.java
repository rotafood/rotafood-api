package br.com.rotafood.api.application.dto.catalog;

import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.Category;
import br.com.rotafood.api.domain.entity.catalog.AvailabilityStatus;
import br.com.rotafood.api.domain.entity.catalog.TemplateType;

public record CategoryDto(
    UUID id,
    Integer index,
    String name,
    TemplateType template,
    AvailabilityStatus status
    ) {
    public CategoryDto(Category category) {
        this(
            category.getId(),
            category.getIndex(),
            category.getName(),
            category.getTemplate(),
            category.getStatus()        
            );
    }
}
