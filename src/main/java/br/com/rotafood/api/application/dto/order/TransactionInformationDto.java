package br.com.rotafood.api.application.dto.order;

import java.util.UUID;

import br.com.rotafood.api.domain.entity.order.TransactionInformation;

public record TransactionInformationDto(
    UUID id,
    String authorizationCode,
    String acquirerDocument
) {
    public TransactionInformationDto(TransactionInformation transaction) {
        this(
            transaction.getId(),
            transaction.getAuthorizationCode(),
            transaction.getAcquirerDocument()
        );
    }


}
