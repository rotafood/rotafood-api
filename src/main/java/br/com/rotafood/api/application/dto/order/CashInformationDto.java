package br.com.rotafood.api.application.dto.order;

import java.math.BigDecimal;
import java.util.UUID;

public record CashInformationDto(
    UUID id,
    String description,
    BigDecimal changeFor
) {
}