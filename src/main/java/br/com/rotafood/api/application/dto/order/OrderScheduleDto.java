package br.com.rotafood.api.application.dto.order;

import java.util.Date;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.order.OrderSchedule;

public record OrderScheduleDto(
    UUID id,
    Date deliveryDateTimeStart,
    Date deliveryDateTimeEnd
) {
    public OrderScheduleDto(OrderSchedule schedule) {
        this(
            schedule.getId(),
            schedule.getDeliveryDateTimeStart(),
            schedule.getDeliveryDateTimeEnd()
        );
    }

}

