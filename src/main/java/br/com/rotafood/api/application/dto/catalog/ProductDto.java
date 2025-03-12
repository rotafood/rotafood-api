package br.com.rotafood.api.application.dto.catalog;

import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.DietaryRestrictions;
import br.com.rotafood.api.domain.entity.catalog.PackagingType;
import br.com.rotafood.api.domain.entity.catalog.Product;
import br.com.rotafood.api.domain.entity.catalog.Serving;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;


@Valid
public record ProductDto(
    UUID id,
    @NotNull
    String name,
    String description,
    String ean,
    String additionalInformation,
    List<DietaryRestrictions> dietaryRestrictions,
    Serving serving,
    List<String> tags,
    String imagePath,
    List<ProductOptionGroupDto> optionGroups,
    List<ProductPackagingDto> packagings,
    PackagingType packagingType,
    Integer quantity
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
                    .toList()
                : List.of(),
            product.getServing(),
            product.getTags(),
            product.getImagePath(),
            product.getProductOptionGroups() != null ? product.getProductOptionGroups().stream().map(ProductOptionGroupDto::new).toList() : null,
            product.getProductPackagings() != null ? product.getProductPackagings().stream().map(ProductPackagingDto::new).toList() : null,
            product.getPackagingType(),
            product.getQuantity()
        );
    }
}
