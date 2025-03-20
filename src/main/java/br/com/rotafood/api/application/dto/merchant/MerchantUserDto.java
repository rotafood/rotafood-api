package br.com.rotafood.api.application.dto.merchant;

import java.util.UUID;

import br.com.rotafood.api.domain.entity.merchant.MerchantUser;
import br.com.rotafood.api.domain.entity.merchant.MerchantUserRole;

public record MerchantUserDto(
    UUID id,
    String name,
    String email,
    String phone,
    MerchantUserRole role,
    UUID merchantId
) {
    public MerchantUserDto(MerchantUser merchantUser) {
        this(
            merchantUser.getId(),
            merchantUser.getName(),
            merchantUser.getEmail(),
            merchantUser.getPhone(),
            merchantUser.getRole(),
            merchantUser.getMerchant().getId()
        );
    }
}
