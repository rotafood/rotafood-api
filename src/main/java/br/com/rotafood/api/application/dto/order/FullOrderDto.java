package br.com.rotafood.api.application.dto.order;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.application.dto.merchant.MerchantDto;
import br.com.rotafood.api.domain.entity.order.Order;
import br.com.rotafood.api.domain.entity.order.OrderSalesChannel;
import br.com.rotafood.api.domain.entity.order.OrderStatus;
import br.com.rotafood.api.domain.entity.order.OrderTiming;
import br.com.rotafood.api.domain.entity.order.OrderType;

public record FullOrderDto(
    UUID id,
    Date modifiedAt,
    Date createdAt,
    OrderType type,
    OrderStatus status,
    Date preparationStartDateTime,
    OrderSalesChannel salesChannel,
    OrderTiming timing,
    String extraInfo,
    MerchantDto merchant,
    OrderTotalDto total,
    OrderCustomerDto customer,
    OrderDeliveryDto delivery,
    OrderScheduleDto schedule,
    OrderIndoorDto indoor,
    OrderTakeoutDto takeout,
    OrderPaymentDto payment,
    List<OrderItemDto> items,
    List<OrderBenefitDto> benefits,
    List<OrderAdditionalFeeDto> additionalFees
) {
    public FullOrderDto(Order order) {
        this(
            order.getId(),
            order.getModifiedAt(),
            order.getCreatedAt(),
            order.getType(),
            order.getStatus(),
            order.getPreparationStartDateTime(),
            order.getSalesChannel(),
            order.getTiming(),
            order.getExtraInfo(),
            new MerchantDto(order.getMerchant()),
            order.getTotal() != null ? new OrderTotalDto(order.getTotal()) : null,
            order.getCustomer() != null ? new OrderCustomerDto(order.getCustomer()) : null,
            order.getDelivery() != null ? new OrderDeliveryDto(order.getDelivery()) : null,
            order.getSchedule() != null ? new OrderScheduleDto(order.getSchedule()) : null,
            order.getIndoor() != null ? new OrderIndoorDto(order.getIndoor()) : null,
            order.getTakeout() != null ? new OrderTakeoutDto(order.getTakeout()) : null,
            order.getPayment() != null ? new OrderPaymentDto(order.getPayment()) : null,
            order.getItems() != null ? order.getItems().stream().map(OrderItemDto::new).toList() : List.of(),
            order.getBenefits() != null ? order.getBenefits().stream().map(OrderBenefitDto::new).toList() : List.of(),
            order.getAdditionalFees() != null ? order.getAdditionalFees().stream().map(OrderAdditionalFeeDto::new).toList() : List.of()
        );
    }
    
}

