package br.com.rotafood.api.application.dto.order;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.order.OrderBenefit;
import br.com.rotafood.api.domain.entity.order.OrderBenefitTarget;



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