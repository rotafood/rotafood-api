package br.com.rotafood.api.application.dto.order;

import java.util.Date;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.order.OrderDiveIn;

public record OrderDiveInDto(
    UUID id,
    Date deliveryDateTime
) {
    public OrderDiveInDto(OrderDiveIn indoor) {
    this(
        indoor.getId(),
        indoor.getDeliveryDateTime()
    );
}

}

