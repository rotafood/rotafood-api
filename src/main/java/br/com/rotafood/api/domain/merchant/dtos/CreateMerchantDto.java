package br.com.rotafood.api.domain.merchant.dtos;

import br.com.rotafood.api.domain.address.dtos.AddressDto;
import br.com.rotafood.api.domain.merchant.models.MerchantType;

public record CreateMerchantDto (
    String name,
    String corporateName,
    String description,
    String document,
    MerchantType merchantType,
    AddressDto address,
    CreateOwnerUser owner
) {}
