package br.com.rotafood.api.application.dto.payment;

import java.math.BigDecimal;
import java.time.Instant;

public record StripeChargeDto(
    String id,
    BigDecimal amount,
    Instant created,
    String currency
) {}
