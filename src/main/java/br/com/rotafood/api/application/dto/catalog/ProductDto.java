package br.com.rotafood.api.application.dto.catalog;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import br.com.rotafood.api.domain.entity.catalog.DietaryRestrictions;
import br.com.rotafood.api.domain.entity.catalog.Product;
import br.com.rotafood.api.domain.entity.catalog.Serving;

public record ProductDto(
    UUID id,
    String name,
    String description,
    String ean,
    String additionalInformation,
    List<DietaryRestrictions> dietaryRestrictions,
    SellingOptionDto sellingOption,
    UUID itemId,
    UUID optionId,
    WeightDto weight,
    Serving serving,
    List<String> tags,
    String imagePath,
    List<String> multipleImages
) {
    public ProductDto(Product product) {
        this(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getEan(),
            product.getAdditionalInformation(),
            product.getDietaryRestrictions() != null 
                ? product.getDietaryRestrictions().stream()
                    .map(DietaryRestrictions::valueOf)
                    .collect(Collectors.toList())
                : List.of(),
            product.getSellingOption() != null ? new SellingOptionDto(product.getSellingOption()) : null,
            product.getItem() != null ? product.getItem().getId() : null,
            product.getOption() != null ? product.getOption().getId() : null,
            product.getWeight() != null ? new WeightDto(product.getWeight()) : null,
            product.getServing(),
            product.getTags(),
            product.getImagePath(),
            product.getMultipleImages()
        );
    }
}
