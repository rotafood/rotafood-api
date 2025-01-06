package br.com.rotafood.api.application.dto.order;

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
) {}
