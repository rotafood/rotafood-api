package br.com.rotafood.api.catalog.application.dto;

import java.util.List;
import jakarta.validation.Valid;

@Valid
public record CategoriesAggregatedDto(
    List<CategoryDto> categories,
    List<ItemDto> items,
    List<OptionGroupDto> optionGroups
) {
}
