package br.com.rotafood.api.application.dto.merchant;

import br.com.rotafood.api.application.dto.AddressDto;
import br.com.rotafood.api.domain.entity.merchant.DocumentType;
import br.com.rotafood.api.domain.entity.merchant.MerchantType;
import jakarta.validation.constraints.NotBlank;

public record MerchantCreateDto (
    @NotBlank()
    String name,

    String corporateName,
    
    @NotBlank()
    String description,

    DocumentType documentType,

    @NotBlank()
    String document,

    @NotBlank()
    String phone,

    MerchantType merchantType,
    
    AddressDto address
) {}
