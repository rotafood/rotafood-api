package br.com.rotafood.api.application.dto.order;

import java.util.UUID;

import br.com.rotafood.api.domain.entity.order.OrderDigitalWalletInformation;

public record OrderDigitalWalletInformationDto(
    UUID id,
    String walletName,
    String walletId
) {
    public OrderDigitalWalletInformationDto(OrderDigitalWalletInformation wallet) {
    this(
        wallet.getId(),
        wallet.getWalletName(),
        wallet.getWalletId()
    );
}

}
