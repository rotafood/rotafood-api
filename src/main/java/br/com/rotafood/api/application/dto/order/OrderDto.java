package br.com.rotafood.api.application.dto.order;


import java.util.Date;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.order.*;
import br.com.rotafood.api.infra.utils.DateUtils;

public record OrderDto(
    UUID id,
    Date modifiedAt,
    Date createdAt,
    Date preparationStartDateTime,
    OrderType type,
    OrderStatus status,
    OrderSalesChannel salesChannel,
    OrderTiming timing,
    String extraInfo,
    UUID merchantId,
    OrderTotalDto total
) {
    public OrderDto(Order order) {
        this(
            order.getId(),
            DateUtils.convertInstantToDate(order.getModifiedAt()),
            DateUtils.convertInstantToDate(order.getCreatedAt()),
            DateUtils.convertInstantToDate(order.getPreparationStartDateTime()),
            order.getType(),
            order.getStatus(),
            order.getSalesChannel(),
            order.getTiming(),
            order.getExtraInfo(),
            order.getMerchant().getId(),
            order.getTotal() != null ? new OrderTotalDto(order.getTotal()) : null
        );
    }
}
