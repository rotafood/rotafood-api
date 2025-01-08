package br.com.rotafood.api.application.dto.order;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.order.OrderBenefit;



public record OrderBenefitDto(
    UUID id,
    BigDecimal value,
    String target
) {
    public OrderBenefitDto(OrderBenefit benefit) {
        this(
            benefit.getId(),
            benefit.getValue(),
            benefit.getTarget()
        );
    }

}