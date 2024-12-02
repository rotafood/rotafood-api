package br.com.rotafood.api.application.dto.catalog;

import java.util.List;

public record FullItemDto(
    ItemDto item,
    List<ProductDto> products,
    List<OptionGroupDto> optionGroups,
    List<OptionDto> options
) {}
