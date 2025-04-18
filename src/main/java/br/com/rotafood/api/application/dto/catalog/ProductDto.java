package br.com.rotafood.api.application.dto.catalog;

import java.util.UUID;

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
    Serving serving,
    String imagePath,
    PackagingType packagingType,
    ProductPackagingDto packaging,    
    Integer quantity
) {
    public ProductDto(Product product) {
        this(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getEan(),
            product.getAdditionalInformation(),
            product.getServing(),
            product.getImagePath(),
            product.getPackagingType(),
            product.getProductPackaging() != null ? new ProductPackagingDto(product.getProductPackaging()) : null,         
            product.getQuantity()
        );
    }
}
