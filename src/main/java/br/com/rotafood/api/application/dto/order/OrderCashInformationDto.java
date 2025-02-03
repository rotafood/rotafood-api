package br.com.rotafood.api.application.dto.order;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.order.OrderCashInformation;

public record OrderCashInformationDto(
    UUID id,
    String description,
    BigDecimal changeFor
) {

    public OrderCashInformationDto(OrderCashInformation cash) {
    this(
        cash.getId(),
        cash.getDescription(),
        cash.getChangeFor()
    );
}

}