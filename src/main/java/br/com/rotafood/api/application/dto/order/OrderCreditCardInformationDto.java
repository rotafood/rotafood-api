package br.com.rotafood.api.application.dto.order;

import java.util.UUID;

import br.com.rotafood.api.domain.entity.order.OrderCreditCardInformation;

public record OrderCreditCardInformationDto(
    UUID id,
    String brand
) {
    public OrderCreditCardInformationDto(OrderCreditCardInformation card) {
    this(
        card.getId(),
        card.getBrand()
    );
}

}