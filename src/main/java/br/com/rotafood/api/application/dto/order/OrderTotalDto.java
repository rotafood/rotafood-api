package br.com.rotafood.api.application.dto.order;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderTotalDto(
    UUID id,
    BigDecimal benefits,
    BigDecimal deliveryFee,
    BigDecimal orderAmount,
    BigDecimal subTotal,
    BigDecimal additionalFees
) {}

