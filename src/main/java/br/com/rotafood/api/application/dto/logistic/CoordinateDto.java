package br.com.rotafood.api.application.dto.logistic;

import java.math.BigDecimal;

public record CoordinateDto (
    BigDecimal lat,
    BigDecimal lng
) {
    
}
