package br.com.rotafood.api.application.dto.catalog;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.DietaryRestrictions;
import br.com.rotafood.api.domain.entity.catalog.ProductType;
import br.com.rotafood.api.domain.entity.catalog.Serving;
import br.com.rotafood.api.domain.entity.catalog.WeightUnit;

public record GetItemDto(
    UUID id,

    String name,
    String description,
    String ean,
    Serving serving,
    String additionalInformation,
    ProductType productType,
    WeightUnit weightUnit,
    BigDecimal weightQuantity,
    SellingOptionDto sellingOption,
    PizzaWeightDto weight,
    List<String> tags,
    Optional<String> image,
    List<DietaryRestrictions> dietaryRestrictions,
    Optional<List<String>> multipleImages,
    Integer index,
    PriceDto priceDto,
    boolean hasOptionGroups,
    List<OptionGroupDto> optionGroup,
    List<ShiftDto> shifts
) {
    
}
