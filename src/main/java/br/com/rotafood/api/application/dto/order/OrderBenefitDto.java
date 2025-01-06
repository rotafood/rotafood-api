package br.com.rotafood.api.application.dto.order;

import java.math.BigDecimal;
import java.util.UUID;


public record OrderBenefitDto(
    UUID id,
    BigDecimal value,
    String target
) {}