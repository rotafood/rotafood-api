package br.com.rotafood.api.application.dto.catalog;

import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.Status;

public record OptionDto(
    UUID id,
    Status status,
    Integer index,
    PriceDto price,
    UUID productId
) {
}
