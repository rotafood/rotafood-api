package br.com.rotafood.api.dto.logistic;

import java.math.BigDecimal;

public record CoordinateDto (
    BigDecimal latitude,
    BigDecimal longitude
) {
    
}
