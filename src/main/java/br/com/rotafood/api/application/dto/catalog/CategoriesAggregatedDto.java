package br.com.rotafood.api.application.dto.catalog;

import java.util.List;
import jakarta.validation.Valid;

@Valid
public record CategoriesAggregatedDto(
    List<CategoryDto> categories,
    List<ItemDto> items,
    List<OptionGroupDto> optionGroups
) {
}
