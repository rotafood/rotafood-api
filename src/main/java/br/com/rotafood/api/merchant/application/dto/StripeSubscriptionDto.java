package br.com.rotafood.api.merchant.application.dto;

import java.time.Instant;

public record StripeSubscriptionDto(
    String id,
    String status,
    Instant startDate,
    Instant endedAt
) {}
