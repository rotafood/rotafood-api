package br.com.rotafood.api.application.dto.merchant;


public record MerchantUserCreateDto(
    String name,
    String email,
    String phone,
    String password,
    MerchantUserRoleCreate role
) {}
