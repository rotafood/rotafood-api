package br.com.rotafood.api.application.dto.order;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.order.CashInformation;

public record CashInformationDto(
    UUID id,
    String description,
    BigDecimal changeFor
) {

    public CashInformationDto(CashInformation cash) {
    this(
        cash.getId(),
        cash.getDescription(),
        cash.getChangeFor()
    );
}

}