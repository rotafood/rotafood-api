package br.com.rotafood.api.merchant.application.dto;

import br.com.rotafood.api.merchant.domain.entity.MerchantUserRole;
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
