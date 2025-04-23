package br.com.rotafood.api.application.dto.address;


public record BrasilApiResponse(
    String cep,
    String state,
    String city,
    String neighborhood,
    String street,
    BrasilApiLocation location
) {
}
