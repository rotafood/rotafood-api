package br.com.rotafood.api.application.dto.order;

import java.util.Date;
import java.util.UUID;

public record OrderScheduleDto(
    UUID id,
    Date deliveryDateTimeStart,
    Date deliveryDateTimeEnd
) {}

