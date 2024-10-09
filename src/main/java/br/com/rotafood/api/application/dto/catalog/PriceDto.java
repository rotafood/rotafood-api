package br.com.rotafood.api.application.dto.catalog;

import java.math.BigDecimal;

public record PriceDto(
    BigDecimal value,
    BigDecimal originalValue
) {
}
