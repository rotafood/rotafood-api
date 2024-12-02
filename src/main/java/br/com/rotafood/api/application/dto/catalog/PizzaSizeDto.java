package br.com.rotafood.api.application.dto.catalog;

import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.PizzaSize;
import br.com.rotafood.api.domain.entity.catalog.Status;

public record PizzaSizeDto(
    UUID id,
    String name,
    Integer index,
    Status status,
    Integer slices,
    List<Integer> acceptedFractions,
    PriceDto price
) {
    public PizzaSizeDto(PizzaSize size) {
        this(
            size.getId(),
            size.getName(),
            size.getIndex(),
            size.getStatus(),
            size.getSlices(),
            size.getAcceptedFractions(),
            size.getPrice() != null ? new PriceDto(size.getPrice()) : null
        );
    }
}
