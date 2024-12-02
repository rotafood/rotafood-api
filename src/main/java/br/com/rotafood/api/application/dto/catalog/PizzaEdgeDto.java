package br.com.rotafood.api.application.dto.catalog;

import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.PizzaEdge;
import br.com.rotafood.api.domain.entity.catalog.Status;

public record PizzaEdgeDto(
    UUID id,
    String name,
    Integer index,
    Status status,
    PriceDto price
) {
    public PizzaEdgeDto(PizzaEdge edge) {
        this(
            edge.getId(),
            edge.getName(),
            edge.getIndex(),
            edge.getStatus(),
            edge.getPrice() != null ? new PriceDto(edge.getPrice()) : null
        );
    }
}
