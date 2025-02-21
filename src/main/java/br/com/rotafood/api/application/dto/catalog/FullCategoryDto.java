package br.com.rotafood.api.application.dto.catalog;

import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.Category;
import br.com.rotafood.api.domain.entity.catalog.AvailabilityStatus;
import br.com.rotafood.api.domain.entity.catalog.TemplateType;

public record FullCategoryDto(
    UUID id,
    Integer index,
    String name, 
    TemplateType template,
    AvailabilityStatus status,
    List<ItemDto> items
) {
    public FullCategoryDto(Category category) {
        this(
            category.getId(),
            category.getIndex(),
            category.getName(),
            category.getTemplate(),
            category.getStatus(),
            category.getItems().stream().map(ItemDto::new).toList()
        );
    }
}
