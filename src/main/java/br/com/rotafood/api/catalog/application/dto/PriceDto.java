package br.com.rotafood.api.catalog.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.rotafood.api.catalog.domain.entity.Price;

public record PriceDto(
    UUID id,
    BigDecimal value,
    BigDecimal originalValue
) {
    public PriceDto(Price price) {
        this(
            price.getId(),
            price.getValue(),
            price.getOriginalValue()
        );
    }
}
