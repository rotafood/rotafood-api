package br.com.rotafood.api.application.dto.order;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

import br.com.rotafood.api.domain.entity.order.*;
import br.com.rotafood.api.infra.utils.DateUtils;

public record FullOrderDto(
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
         Optional.ofNullable(order.getItems()).map(items -> items.stream().map(OrderItemDto::new).toList()).orElse(List.of()),
         Optional.ofNullable(order.getBenefits()).map(benefits -> benefits.stream().map(OrderBenefitDto::new).toList()).orElse(List.of()),
         Optional.ofNullable(order.getAdditionalFees()).map(fees -> fees.stream().map(OrderAdditionalFeeDto::new).toList()).orElse(List.of())
         );
    }
}
