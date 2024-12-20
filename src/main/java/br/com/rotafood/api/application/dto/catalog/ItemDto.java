package br.com.rotafood.api.application.dto.catalog;

import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.Item;
import br.com.rotafood.api.domain.entity.catalog.Status;
import br.com.rotafood.api.domain.entity.catalog.TemplateType;

public record ItemDto(
    UUID id,
    Status status,
    Integer index,
    TemplateType type,
    UUID categoryId,
    List<ShiftDto> shifts,
    List<ContextModifierDto> contextModifiers,
    ProductDto product
) {
    public ItemDto(Item item) {
        this(
            item.getId(),
            item.getStatus(),
            item.getIndex(),
            item.getType(),
            item.getCategory().getId(),
            item.getShifts() != null ? item.getShifts().stream().map(ShiftDto::new).toList() : null,
            item.getContextModifiers() != null ? item.getContextModifiers().stream().map(ContextModifierDto::new).toList() : null,
            new ProductDto(item.getProduct())
        );
    }
}
