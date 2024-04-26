package br.com.rotafood.api.domain.merchant.dtos;

import java.util.Date;
import java.util.UUID;

import br.com.rotafood.api.domain.merchant.models.MerchantType;

public record MerchantDto (
    UUID id,
    String name,
    String corporateName,
    String description,
    String document,
    MerchantType merchantType,
    Date createdAt
    ) {}
