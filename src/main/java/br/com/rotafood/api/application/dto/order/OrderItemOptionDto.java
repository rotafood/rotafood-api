package br.com.rotafood.api.application.dto.order;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.CatalogContext;
import br.com.rotafood.api.domain.entity.order.OrderItemOption;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderItemOptionDto(
    UUID id,
    @Min(1)
    int quantity,
    @Min(0)
    BigDecimal totalPrice,
    @NotNull
    CatalogContext catalogContext,
    @NotNull
    String groupName,
    @NotNull
    UUID groupId,
    @NotNull
    @Valid
    OrderOptionDetailDto option
) {
    public OrderItemOptionDto(OrderItemOption orderItemOption) {
        this(
            orderItemOption.getId(),
            orderItemOption.getQuantity(),
            orderItemOption.getTotalPrice(),
            orderItemOption.getCatalogContext(),
            orderItemOption.getOption() != null ? orderItemOption.getOption().getOptionGroup().getName() : null,
            orderItemOption.getOption() != null ? orderItemOption.getOption().getOptionGroup().getId() : null,
            orderItemOption.getOption() != null ? new OrderOptionDetailDto(orderItemOption.getOption(), orderItemOption.getCatalogContext()) : null
        );
    }

}
