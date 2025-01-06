package br.com.rotafood.api.application.dto.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderPaymentDto(
    UUID id,
    String description,
    List<OrderPaymentMethodDto> methods,
    BigDecimal pending,
    BigDecimal prepaid
) {}
