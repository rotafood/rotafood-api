package br.com.rotafood.api.application.dto.payment;

import java.util.List;

public record StripePaymentStatusDto(
    boolean active,
    String message,
    String email,
    int totalSubscriptions,
    List<StripeSubscriptionDto> subscriptions,
    List<StripeChargeDto> payments
) {}
