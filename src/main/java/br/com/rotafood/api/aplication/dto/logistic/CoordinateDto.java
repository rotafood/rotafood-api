package br.com.rotafood.api.aplication.dto.logistic;

import java.math.BigDecimal;

public record CoordinateDto (
    BigDecimal lat,
    BigDecimal lng
) {
    
}
