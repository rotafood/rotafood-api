package br.com.rotafood.api.application.dto.catalog;

import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.Pizza;

public record PizzaDto(
    UUID id,
    List<PizzaSizeDto> sizes,
    List<PizzaCrushDto> crusts,
    List<PizzaEdgeDto> edges,
    List<PizzaToppingDto> toppings,
    List<ShiftDto> shifts,
    UUID iFoodPizzaId
) {
    public PizzaDto(Pizza pizza) {
        this(
            pizza.getId(),
            pizza.getSizes().stream().map(PizzaSizeDto::new).toList(),
            pizza.getCrusts().stream().map(PizzaCrushDto::new).toList(),
            pizza.getEdges().stream().map(PizzaEdgeDto::new).toList(),
            pizza.getToppings().stream().map(PizzaToppingDto::new).toList(),
            pizza.getShifts().stream().map(ShiftDto::new).toList(),
            pizza.getIFoodPizzaId()
        );
    }
}
