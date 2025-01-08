package br.com.rotafood.api.application.dto.order;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.order.OrderTotal;

public record OrderTotalDto(
    UUID id,
    BigDecimal benefits,
    BigDecimal deliveryFee,
    BigDecimal orderAmount,
    BigDecimal subTotal,
    BigDecimal additionalFees
) {
    public OrderTotalDto(OrderTotal total) {
        this(
            total.getId(),
            total.getBenefits(),
            total.getDeliveryFee(),
            total.getOrderAmount(),
            total.getSubTotal(),
            total.getAdditionalFees()
        );
    }

}

