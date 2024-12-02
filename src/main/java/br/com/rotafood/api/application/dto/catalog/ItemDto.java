package br.com.rotafood.api.application.dto.catalog;

import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.Item;
import br.com.rotafood.api.domain.entity.catalog.Status;

public record ItemDto(
    UUID id,
    Status status,
    Integer index,
    PriceDto price,
    List<ShiftDto> shifts,
    List<ContextModifierDto> contextModifiers,
    ProductDto product,
    List<GetOptionGroupDto> optionGroups
) {
    public ItemDto(Item item) {
        this(
            item.getId(),
            item.getStatus(),
            item.getIndex(),
            item.getPrice() != null ? new PriceDto(item.getPrice()) : null,
            item.getShifts().stream().map(ShiftDto::new).toList(),
            item.getContextModifiers().stream().map(ContextModifierDto::new).toList(),
            item.getProduct() != null ? new ProductDto(item.getProduct()) : null,
            item.getItemOptionGroups().stream().map(obj -> new GetOptionGroupDto(obj.getIndex(), obj.getOptionGroup())).toList()
        );
    }
}
