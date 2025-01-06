package br.com.rotafood.api.application.dto.order;

import java.util.UUID;

public record DigitalWalletInformationDto(
    UUID id,
    String walletName,
    String walletId
) {}
