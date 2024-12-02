package br.com.rotafood.api.application.dto.merchant;

import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.application.dto.address.AddressDto;
import br.com.rotafood.api.domain.entity.merchant.MerchantPermission;
import br.com.rotafood.api.domain.entity.merchant.MerchantUser;

public record MerchantUserDto(
    UUID id,

    String name,
    String email,
    String phone,
    String document,
    List<MerchantPermission> merchantPermissions,
    MerchantDto merchant
) {
    public MerchantUserDto(MerchantUser merchantUser) {
        this(
            merchantUser.getId(),
            merchantUser.getName(),
            merchantUser.getEmail(),
            merchantUser.getPhone(),
            merchantUser.getDocument(),
            merchantUser.getMerchantPermissions(),
            new MerchantDto(
                merchantUser.getMerchant().getId(),
                merchantUser.getMerchant().getName(),
                merchantUser.getMerchant().getCorporateName(),
                merchantUser.getMerchant().getDescription(),
                merchantUser.getMerchant().getDocument(),
                merchantUser.getMerchant().getMerchantType(),
                merchantUser.getMerchant().getCreatedAt(),
                new AddressDto(
                    merchantUser.getMerchant().getAddress().getId(),
                    merchantUser.getMerchant().getAddress().getCountry(),
                    merchantUser.getMerchant().getAddress().getState(),
                    merchantUser.getMerchant().getAddress().getCity(),
                    merchantUser.getMerchant().getAddress().getNeighborhood(),
                    merchantUser.getMerchant().getAddress().getPostalCode(),
                    merchantUser.getMerchant().getAddress().getStreetName(),
                    merchantUser.getMerchant().getAddress().getStreetNumber(),
                    merchantUser.getMerchant().getAddress().getFormattedAddress(),
                    merchantUser.getMerchant().getAddress().getComplement(),
                    merchantUser.getMerchant().getAddress().getLatitude(),
                    merchantUser.getMerchant().getAddress().getLongitude()
                )
            )
        );
    }
}
