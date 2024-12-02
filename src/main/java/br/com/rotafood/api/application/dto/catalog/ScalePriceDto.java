package br.com.rotafood.api.application.dto.catalog;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.ScalePrice;

public record ScalePriceDto(
    UUID id,
    Integer minQuantity,
    BigDecimal price
) {
    public ScalePriceDto(ScalePrice scalePrice) {
        this(
            scalePrice.getId(),
            scalePrice.getMinQuantity(),
            scalePrice.getValue()
        );
    }
}
