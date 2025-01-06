package br.com.rotafood.api.application.dto.order;

import br.com.rotafood.api.application.dto.address.AddressDto;
import br.com.rotafood.api.domain.entity.order.OrderDeliveryBy;
import br.com.rotafood.api.domain.entity.order.OrderDeliveryDescription;
import br.com.rotafood.api.domain.entity.order.OrderDeliveryMode;

import java.util.Date;
import java.util.UUID;

public record OrderDeliveryDto(
    UUID id,
    OrderDeliveryMode mode,
    OrderDeliveryBy deliveryBy,
    OrderDeliveryDescription description,
    String pickupCode,
    Date deliveryDateTime,
    AddressDto address
) {}
