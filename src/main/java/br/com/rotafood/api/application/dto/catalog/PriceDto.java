package br.com.rotafood.api.application.dto.catalog;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.Price;

public record PriceDto(
    UUID id,
    BigDecimal value,
    BigDecimal originalValue,
    List<ScalePriceDto> scalePrices
) {
    public PriceDto(Price price) {
        this(
            price.getId(),
            price.getValue(),
            price.getOriginalValue(),
            price.getScalePrices().stream().map(ScalePriceDto::new).toList()
        );
    }
}
