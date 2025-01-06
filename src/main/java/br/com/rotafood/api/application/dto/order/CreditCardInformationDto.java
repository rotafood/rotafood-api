package br.com.rotafood.api.application.dto.order;

import java.util.UUID;

public record CreditCardInformationDto(
    UUID id,
    String brand
) {
}