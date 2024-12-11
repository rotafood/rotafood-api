package br.com.rotafood.api.application.dto.catalog;

import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.Option;
import br.com.rotafood.api.domain.entity.catalog.Status;

public record OptionDto (
    UUID id,
    Status status,
    Integer index,
    PriceDto price,
    ProductDto product
) {
    public OptionDto(Option option) {
        this(
            option.getId(),
            option.getStatus(),
            option.getIndex(),
            option.getPrice() != null ? new PriceDto(option.getPrice()) : null,
            option.getProduct() != null ? new ProductDto(option.getProduct()) : null
        );
    }
}
