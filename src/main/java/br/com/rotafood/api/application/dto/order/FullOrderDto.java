package br.com.rotafood.api.application.dto.order;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.application.dto.merchant.MerchantDto;
import br.com.rotafood.api.domain.entity.order.OrderSalesChannel;
import br.com.rotafood.api.domain.entity.order.OrderTiming;
import br.com.rotafood.api.domain.entity.order.OrderType;

public record FullOrderDto(
    UUID id,
    Date modifiedAt,
    Date createdAt,
    OrderType orderType,
    Date preparationStartDateTime,
    OrderSalesChannel salesChannel,
    OrderTiming orderTiming,
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
) {}

