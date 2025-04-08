package br.com.rotafood.api.application.dto.order;

import br.com.rotafood.api.application.dto.AddressDto;
import br.com.rotafood.api.domain.entity.order.OrderDelivery;
import br.com.rotafood.api.domain.entity.order.OrderDeliveryBy;
import br.com.rotafood.api.domain.entity.order.OrderDeliveryMode;

import java.util.Date;
import java.util.UUID;

public record OrderDeliveryDto(
    UUID id,
    OrderDeliveryBy deliveryBy,
    String description,
    String pickupCode,
    OrderDeliveryMode mode,
    Date deliveryDateTime,
    AddressDto address
) {
    public OrderDeliveryDto(OrderDelivery delivery) {
    this(
        delivery.getId(),
        delivery.getDeliveryBy(),
        delivery.getDescription(),
        delivery.getPickupCode(),
        delivery.getMode(),
        delivery.getDeliveryDateTime(),
        delivery.getAddress() != null ? new AddressDto(delivery.getAddress()) : null
    );
}

}
