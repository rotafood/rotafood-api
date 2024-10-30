package br.com.rotafood.api.application.dto.merchant;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record OwnerCreateDto (
    @NotBlank(message = "O campo é requerido.")
    String name,
    
    @NotBlank(message = "O campo é requerido.")
    @Email(message = "O e-mail deve ser válido.")
    String email,
    
    @NotBlank(message = "O campo é requerido.")
    String password,
    
    @NotBlank(message = "O campo é requerido.")
    String phone,
    
    @NotBlank(message = "O campo é requerido.")
    String document
) {}
