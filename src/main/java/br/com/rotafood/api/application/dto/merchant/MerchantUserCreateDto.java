package br.com.rotafood.api.application.dto.merchant;

import br.com.rotafood.api.domain.entity.merchant.MerchantUserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MerchantUserCreateDto(
    @NotBlank
    String name,
    @NotBlank
    String email,
    @NotBlank
    String phone,
    @NotBlank
    String password,
    @NotNull
    MerchantUserRole role
) {}
