package br.com.rotafood.api.application.dto.order;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

import br.com.rotafood.api.domain.entity.order.*;
import br.com.rotafood.api.infra.utils.DateUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


public record FullOrderDto(
    UUID id,
    Date modifiedAt,
    Date createdAt,
    @NotNull
    Date preparationStartDateTime,
    @NotNull
    OrderType type,
    @NotNull
    OrderStatus status,
    @NotNull
    OrderSalesChannel salesChannel,
    @NotNull
    OrderTiming timing,
    String extraInfo,


    @NotNull
    UUID merchantId,

    OrderTotalDto total,

    @NotNull
    @Valid
    OrderCustomerDto customer,
    OrderDeliveryDto delivery,
    OrderScheduleDto schedule,
    OrderIndoorDto indoor,
    OrderTakeoutDto takeout,

    @Valid
    @NotNull
    OrderPaymentDto payment,

    @Valid
    @NotEmpty
    List<OrderItemDto> items,

    List<OrderBenefitDto> benefits,
    
    List<OrderAdditionalFeeDto> additionalFees
) {
    public FullOrderDto(Order order) {
         this( order.getId(),
         DateUtils.convertInstantToDate(order.getModifiedAt()),
         DateUtils.convertInstantToDate(order.getCreatedAt()),
         DateUtils.convertInstantToDate(order.getPreparationStartDateTime()),
         order.getType(),
         order.getStatus(),
         order.getSalesChannel(),
         order.getTiming(),
         order.getExtraInfo(),
         order.getMerchant().getId(),
         Optional.ofNullable(order.getTotal()).map(OrderTotalDto::new).orElse(null),
         Optional.ofNullable(order.getCustomer()).map(OrderCustomerDto::new).orElse(null),
         Optional.ofNullable(order.getDelivery()).map(OrderDeliveryDto::new).orElse(null),
         Optional.ofNullable(order.getSchedule()).map(OrderScheduleDto::new).orElse(null),
         Optional.ofNullable(order.getIndoor()).map(OrderIndoorDto::new).orElse(null),
         Optional.ofNullable(order.getTakeout()).map(OrderTakeoutDto::new).orElse(null),
         Optional.ofNullable(order.getPayment()).map(OrderPaymentDto::new).orElse(null),
         order.getItems().stream().map(OrderItemDto::new).toList(),
         Optional.ofNullable(order.getBenefits()).map(benefits -> benefits.stream().map(OrderBenefitDto::new).toList()).orElse(List.of()),
         Optional.ofNullable(order.getAdditionalFees()).map(fees -> fees.stream().map(OrderAdditionalFeeDto::new).toList()).orElse(List.of())
         );
    }
}
