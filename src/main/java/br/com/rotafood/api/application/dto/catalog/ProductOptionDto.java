package br.com.rotafood.api.application.dto.catalog;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.ProductDietaryRestrictions;
import br.com.rotafood.api.domain.entity.catalog.ProductType;
import br.com.rotafood.api.domain.entity.catalog.WeightUnit;

public record ProductOptionDto(
    UUID id,
    String name,
    String description,
    String ean,
    String additionalInformation,
    ProductType productType,
    List<ProductDietaryRestrictions> dietaryRestrictions,
    WeightUnit weightUnit,
    BigDecimal weightQuantity,
    Optional<UUID> optionId,
    ProductSellingOptionDto sellingOption,
    String merchantId,
    Optional<String> image
) {
    
}