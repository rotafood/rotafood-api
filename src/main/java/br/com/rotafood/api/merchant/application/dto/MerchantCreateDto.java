package br.com.rotafood.api.merchant.application.dto;

import br.com.rotafood.api.common.application.dto.AddressDto;
import br.com.rotafood.api.merchant.domain.entity.DocumentType;
import br.com.rotafood.api.merchant.domain.entity.MerchantType;
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
