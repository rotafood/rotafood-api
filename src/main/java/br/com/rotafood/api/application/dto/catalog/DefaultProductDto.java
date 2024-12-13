package br.com.rotafood.api.application.dto.catalog;

import java.util.UUID;
import br.com.rotafood.api.domain.entity.catalog.DefaultProduct;

public record DefaultProductDto(
    UUID id,
    String name,
    String description,
    String ean,
    String imagePath,
    String iFoodImagePath
) {
    public DefaultProductDto(DefaultProduct defaultProduct) {
        this(
            defaultProduct.getId(),
            defaultProduct.getName(),
            defaultProduct.getDescription(),
            defaultProduct.getEan(),
            defaultProduct.getImagePath(),
            defaultProduct.getIFoodImagePath()
        );
    }


    
}
