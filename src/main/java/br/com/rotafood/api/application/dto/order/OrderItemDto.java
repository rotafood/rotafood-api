package br.com.rotafood.api.application.dto.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.CatalogContext;
import br.com.rotafood.api.domain.entity.order.OrderItem;


public record OrderItemDto(
    UUID id,
    int quantity,
    BigDecimal totalPrice,
    CatalogContext catalogContext,
    OrderItemDetailDto item,
    List<OrderItemOptionDto> options

) {
    public OrderItemDto(OrderItem orderItem) {
    this(
        orderItem.getId(),
        orderItem.getQuantity(),
        orderItem.getTotalPrice(),
        orderItem.getCatalogContext(),
        new OrderItemDetailDto(orderItem.getItem(), orderItem.getCatalogContext()),
        orderItem.getOptions() != null ? orderItem.getOptions().stream().map(OrderItemOptionDto::new).toList() : null
    );
}

}
