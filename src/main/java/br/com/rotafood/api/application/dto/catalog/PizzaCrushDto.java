package br.com.rotafood.api.application.dto.catalog;

import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.PizzaCrush;
import br.com.rotafood.api.domain.entity.catalog.Status;

public record PizzaCrushDto(
    UUID id,
    String name,
    Integer index,
    Status status,
    PriceDto price,
    UUID iFoodPizzaCrushId
) {
    public PizzaCrushDto(PizzaCrush crush) {
        this(
            crush.getId(),
            crush.getName(),
            crush.getIndex(),
            crush.getStatus(),
            crush.getPrice() != null ? new PriceDto(crush.getPrice()) : null,
            crush.getIFoodPizzaCrushId()
        );
    }
}
