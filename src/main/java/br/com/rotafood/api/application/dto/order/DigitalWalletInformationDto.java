package br.com.rotafood.api.application.dto.order;

import java.util.UUID;

import br.com.rotafood.api.domain.entity.order.DigitalWalletInformation;

public record DigitalWalletInformationDto(
    UUID id,
    String walletName,
    String walletId
) {
    public DigitalWalletInformationDto(DigitalWalletInformation wallet) {
    this(
        wallet.getId(),
        wallet.getWalletName(),
        wallet.getWalletId()
    );
}

}
