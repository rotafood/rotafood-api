package br.com.rotafood.api.dto.merchant;

import java.util.Date;
import java.util.UUID;

import br.com.rotafood.api.domain.merchant.MerchantType;
import br.com.rotafood.api.dto.address.AddressDto;

public record MerchantDto (
    UUID id,
    String name,
    String corporateName,
    String description,
    String document,
    MerchantType merchantType,
    Date createdAt,
    AddressDto address
    ) {}
