package br.com.rotafood.api.application.dto.order;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.CatalogContext;
import br.com.rotafood.api.domain.entity.order.OrderItemOption;

public record OrderItemOptionDto(
    UUID id,
    int quantity,
    BigDecimal totalPrice,
    CatalogContext catalogContext,
    String groupName,
    UUID groupId,
    OrderOptionDetailDto option
) {
    public OrderItemOptionDto(OrderItemOption orderItemOption) {
        this(
            orderItemOption.getId(),
            orderItemOption.getQuantity(),
            orderItemOption.getTotalPrice(),
            orderItemOption.getCatalogContext(),
            orderItemOption.getOptionGroup() != null ? orderItemOption.getOptionGroup().getName() : null,
            orderItemOption.getOptionGroup() != null ? orderItemOption.getOptionGroup().getId() : null,
            orderItemOption.getOption() != null ? new OrderOptionDetailDto(orderItemOption.getOption(), orderItemOption.getCatalogContext()) : null
        );
    }

}
