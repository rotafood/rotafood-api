package br.com.rotafood.api.application.dto.catalog;

import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.Category;
import br.com.rotafood.api.domain.entity.catalog.Status;

public record GetCategoryDto(
    UUID id,
    Integer index,
    String name, 
    Status status,
    List<ItemDto> items,
    PizzaDto pizza
) {
    public GetCategoryDto(Category category) {
        this(
            category.getId(),
            category.getIndex(),
            category.getName(),
            category.getStatus(),
            category.getItems().stream().map(ItemDto::new).toList(),
            category.getPizza() != null ? new PizzaDto(category.getPizza()) : null
        );
    }
}
