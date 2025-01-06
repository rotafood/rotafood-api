package br.com.rotafood.api.application.dto.merchant;

import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.merchant.MerchantPermission;
import br.com.rotafood.api.domain.entity.merchant.MerchantUser;

public record MerchantUserDto(
    UUID id,
    String name,
    String email,
    String phone,
    List<MerchantPermission> merchantPermissions,
    MerchantDto merchant
) {
    public MerchantUserDto(MerchantUser merchantUser) {
        this(
            merchantUser.getId(),
            merchantUser.getName(),
            merchantUser.getEmail(),
            merchantUser.getPhone(),
            merchantUser.getMerchantPermissions().stream()
            .map(MerchantPermission::valueOf)
            .toList(),
            new MerchantDto(merchantUser.getMerchant())
        );
    }
}
