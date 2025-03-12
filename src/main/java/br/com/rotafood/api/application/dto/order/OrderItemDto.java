package br.com.rotafood.api.application.dto.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.CatalogContext;
import br.com.rotafood.api.domain.entity.order.OrderItem;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderItemDto(
    UUID id,
    @Min(1)
    int quantity,
    @Min(0)
    BigDecimal totalPrice,
    @NotNull
    CatalogContext catalogContext,
    @NotNull
    @Valid
    OrderItemDetailDto item,
    @Valid
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
