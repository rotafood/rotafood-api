package br.com.rotafood.api.application.dto.order;

import java.util.UUID;

public record TransactionInformationDto(
    UUID id,
    String authorizationCode,
    String acquirerDocument
) {

}
