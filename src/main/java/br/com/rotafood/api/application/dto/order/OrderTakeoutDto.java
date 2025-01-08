package br.com.rotafood.api.application.dto.order;

import java.util.Date;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.order.OrderTakeout;
import br.com.rotafood.api.domain.entity.order.OrderTakeoutMode;

public record OrderTakeoutDto(
    UUID id,
    Date takeoutDateTime,
    OrderTakeoutMode mode,
    String comments
) {

    public OrderTakeoutDto(OrderTakeout takeout) {
        this(
            takeout.getId(),
            takeout.getTakeoutDateTime(),
            takeout.getMode(),
            takeout.getComments()
        );
    }

}

