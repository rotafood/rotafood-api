package br.com.rotafood.api.application.dto.order;

import java.util.Date;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.order.OrderIndoor;
import br.com.rotafood.api.domain.entity.order.OrderIndoorMode;

public record OrderIndoorDto(
    UUID id,
    OrderIndoorMode mode,
    Date deliveryDateTime
) {
    public OrderIndoorDto(OrderIndoor indoor) {
    this(
        indoor.getId(),
        indoor.getMode(),
        indoor.getDeliveryDateTime()
    );
}

}

