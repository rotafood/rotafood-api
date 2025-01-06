package br.com.rotafood.api.application.dto.order;

import java.math.BigDecimal;
import java.util.UUID;


public record OrderAdditionalFeeDto(
    UUID id,
    String type,
    BigDecimal value,
    String name,
    String description
) {}