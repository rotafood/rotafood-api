package br.com.rotafood.api.application.dto.order;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.order.OrderAdditionalFee;


public record OrderAdditionalFeeDto(
    UUID id,
    String type,
    BigDecimal value,
    String name,
    String description
) {
    public OrderAdditionalFeeDto(OrderAdditionalFee additionalFee) {
    this(
        additionalFee.getId(),
        additionalFee.getType(),
        additionalFee.getValue(),
        additionalFee.getName(),
        additionalFee.getDescription()
    );
}

}