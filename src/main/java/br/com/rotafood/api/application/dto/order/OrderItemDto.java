package br.com.rotafood.api.application.dto.order;

import java.math.BigDecimal;
import java.util.UUID;


public record OrderItemDto(
    UUID id,
    int quantity,
    BigDecimal totalPrice,
    OrderItemDetailDto item
) {}
