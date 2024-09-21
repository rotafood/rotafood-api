package br.com.rotafood.api.aplication.dto.merchant;

import java.util.Date;
import java.util.UUID;

import br.com.rotafood.api.aplication.dto.address.AddressDto;
import br.com.rotafood.api.domain.entity.merchant.MerchantType;

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
