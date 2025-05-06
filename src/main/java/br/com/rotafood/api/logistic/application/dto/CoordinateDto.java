package br.com.rotafood.api.logistic.application.dto;

import java.math.BigDecimal;

public record CoordinateDto (
    BigDecimal lat,
    BigDecimal lng
) {
    
}
