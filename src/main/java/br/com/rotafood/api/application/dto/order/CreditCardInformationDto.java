package br.com.rotafood.api.application.dto.order;

import java.util.UUID;

import br.com.rotafood.api.domain.entity.order.CreditCardInformation;

public record CreditCardInformationDto(
    UUID id,
    String brand
) {
    public CreditCardInformationDto(CreditCardInformation card) {
    this(
        card.getId(),
        card.getBrand()
    );
}

}