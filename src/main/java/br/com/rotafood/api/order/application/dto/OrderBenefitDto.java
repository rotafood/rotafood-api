package br.com.rotafood.api.order.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.rotafood.api.order.domain.entity.OrderBenefit;
import br.com.rotafood.api.order.domain.entity.OrderBenefitTarget;



public record OrderBenefitDto(
    UUID id,
    BigDecimal value,
    String description,
    OrderBenefitTarget target
) {
    public OrderBenefitDto(OrderBenefit benefit) {
        this(
            benefit.getId(),
            benefit.getValue(),
            benefit.getDescription(),
            benefit.getTarget()
        );
    }

}