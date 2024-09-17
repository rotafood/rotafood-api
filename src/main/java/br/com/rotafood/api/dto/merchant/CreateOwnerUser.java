package br.com.rotafood.api.dto.merchant;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateOwnerUser (
    @NotBlank
    String name,
    @NotBlank
    @Email
    String email,
    @NotBlank
    String password,
    @NotBlank
    String phone,
    @NotBlank
    String document
) {}
