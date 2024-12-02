package br.com.rotafood.api.application.dto.catalog;

import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.PizzaTopping;
import br.com.rotafood.api.domain.entity.catalog.Status;

public record PizzaToppingDto(
    UUID id,
    String name,
    String description,
    String imagePath,
    List<String> dietaryRestrictions,
    Integer index,
    Status status,
    PriceDto price
) {
    public PizzaToppingDto(PizzaTopping topping) {
        this(
            topping.getId(),
            topping.getName(),
            topping.getDescription(),
            topping.getImagePath(),
            topping.getDietaryRestrictions(),
            topping.getIndex(),
            topping.getStatus(),
            topping.getPrice() != null ? new PriceDto(topping.getPrice()) : null
        );
    }
}
