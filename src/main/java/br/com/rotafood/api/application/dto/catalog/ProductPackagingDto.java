package br.com.rotafood.api.application.dto.catalog;

import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.ProductPackaging;

public record ProductPackagingDto(
    UUID id,
    Integer quantityPerPackage,
    boolean useLateralBag,
    PackagingDto packaging
) {
    public ProductPackagingDto(ProductPackaging productPackaging) {
        this(productPackaging.getId(), 
        productPackaging.getQuantityPerPackage(),
        productPackaging.isUseLateralBag(),
        new PackagingDto(productPackaging.getPackaging()));
    }
}