package br.com.rotafood.api.application.dto.order;

import br.com.rotafood.api.domain.entity.order.OrderPaymentMethod;
import br.com.rotafood.api.domain.entity.order.PaymentMethodType;
import br.com.rotafood.api.domain.entity.order.PaymentType;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderPaymentMethodDto(
    UUID id,
    String description,
    PaymentMethodType method,
    boolean prepaid,
    String currency,
    PaymentType type,
    BigDecimal value,
    DigitalWalletInformationDto wallet,
    CashInformationDto cash,
    CreditCardInformationDto card,
    TransactionInformationDto transaction
) {
    public OrderPaymentMethodDto(OrderPaymentMethod method) {
    this(
        method.getId(),
        method.getDescription(),
        method.getMethod(),
        method.isPrepaid(),
        method.getCurrency(),
        method.getType(),
        method.getValue(),
        method.getWallet() != null ? new DigitalWalletInformationDto(method.getWallet()) : null,
        method.getCash() != null ? new CashInformationDto(method.getCash()) : null,
        method.getCard() != null ? new CreditCardInformationDto(method.getCard()) : null,
        method.getTransaction() != null ? new TransactionInformationDto(method.getTransaction()) : null
    );
}

}
