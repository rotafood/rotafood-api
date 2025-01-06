package br.com.rotafood.api.application.dto.order;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemOptionDto(
    UUID id,
    int quantity,
    BigDecimal totalPrice,
    String groupName,
    String groupId,
    OrderOptionDetailDto option
) {}
