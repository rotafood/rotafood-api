package br.com.rotafood.api.application.dto.order;

import java.util.UUID;

import br.com.rotafood.api.domain.entity.order.OrderTransactionInformation;

public record OrderTransactionInformationDto(
    UUID id,
    String authorizationCode,
    String acquirerDocument
) {
    public OrderTransactionInformationDto(OrderTransactionInformation transaction) {
        this(
            transaction.getId(),
            transaction.getAuthorizationCode(),
            transaction.getAcquirerDocument()
        );
    }


}
