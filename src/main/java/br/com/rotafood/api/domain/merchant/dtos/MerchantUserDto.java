package br.com.rotafood.api.domain.merchant.dtos;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.rotafood.api.domain.merchant.models.MerchantPermission;

public record MerchantUserDto (
    Optional<UUID> id,
    String name,
    String email,
    String password,
    String phone,
    List<MerchantPermission> merchantPermissions,
    MerchantDto merchant
) {}