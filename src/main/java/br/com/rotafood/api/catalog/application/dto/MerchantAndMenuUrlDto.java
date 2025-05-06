package br.com.rotafood.api.catalog.application.dto;

import br.com.rotafood.api.merchant.application.dto.FullMerchantDto;

public record MerchantAndMenuUrlDto(
    FullMerchantDto merchant,
    String menuUrl
) {
    
}
