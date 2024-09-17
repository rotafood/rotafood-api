package br.com.rotafood.api.dto.merchant;

import br.com.rotafood.api.domain.merchant.MerchantType;
import br.com.rotafood.api.dto.address.AddressDto;

public record CreateMerchantDto (
    String name,
    String corporateName,
    String description,
    String document,
    MerchantType merchantType,
    AddressDto address,
    CreateOwnerUser owner
) {}
