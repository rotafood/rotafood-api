package br.com.rotafood.api.application.dto.catalog;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.Price;

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
