package br.com.rotafood.api.application.dto.catalog;

import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.PackagingType;
import br.com.rotafood.api.domain.entity.catalog.Product;
import br.com.rotafood.api.domain.entity.catalog.Serving;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;


@Valid
public record ProductOptionDto(
    UUID id,
    @NotNull
    String name,
    String description,
    String ean,
    String additionalInformation,
    Serving serving,
    String imagePath,
    List<ProductPackagingDto> packagings,
    PackagingType packagingType,
    Integer quantity
) {
    public ProductOptionDto(Product product) {
        this(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getEan(),
            product.getAdditionalInformation(),
            product.getServing(),
            product.getImagePath(),
            product.getProductPackagings() != null ? product.getProductPackagings().stream().map(ProductPackagingDto::new).toList() : null,
            product.getPackagingType(),
            product.getQuantity()
        );
    }
}
