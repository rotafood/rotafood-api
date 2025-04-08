package br.com.rotafood.api.application.dto.payment;

import java.time.Instant;

public record StripeSubscriptionDto(
    String id,
    String status,
    Instant startDate,
    Instant endedAt
) {}
