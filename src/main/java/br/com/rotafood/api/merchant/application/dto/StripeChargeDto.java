package br.com.rotafood.api.merchant.application.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record StripeChargeDto(
    String id,
    BigDecimal amount,
    Instant created,
    String currency
) {}
