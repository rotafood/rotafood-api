package br.com.rotafood.api.order.application.dto;


public record BrasilApiResponse(
    String cep,
    String state,
    String city,
    String neighborhood,
    String street,
    BrasilApiLocation location
) {
}
