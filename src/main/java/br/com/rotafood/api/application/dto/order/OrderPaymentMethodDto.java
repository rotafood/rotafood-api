package br.com.rotafood.api.application.dto.order;

import br.com.rotafood.api.domain.entity.order.OrderPaymentMethod;
import br.com.rotafood.api.domain.entity.order.OrderPaymentMethodType;
import br.com.rotafood.api.domain.entity.order.OrderPaymentType;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderPaymentMethodDto(
    UUID id,
    String description,
    OrderPaymentMethodType method,
    boolean prepaid,
    String currency,
    OrderPaymentType type,
    BigDecimal value
) {
    public OrderPaymentMethodDto(OrderPaymentMethod method) {
    this(
        method.getId(),
        method.getDescription(),
        method.getMethod(),
        method.isPrepaid(),
        method.getCurrency(),
        method.getType(),
        method.getValue()
    );
}

}
