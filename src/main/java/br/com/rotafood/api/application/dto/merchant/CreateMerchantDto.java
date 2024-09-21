package br.com.rotafood.api.application.dto.merchant;

import br.com.rotafood.api.application.dto.address.AddressDto;
import br.com.rotafood.api.domain.entity.merchant.MerchantType;

public record CreateMerchantDto (
    String name,
    String corporateName,
    String description,
    String document,
    MerchantType merchantType,
    AddressDto address,
    CreateOwnerUser owner
) {}
