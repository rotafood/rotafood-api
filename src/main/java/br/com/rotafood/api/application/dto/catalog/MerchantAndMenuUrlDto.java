package br.com.rotafood.api.application.dto.catalog;

import br.com.rotafood.api.application.dto.merchant.FullMerchantDto;

public record MerchantAndMenuUrlDto(
    FullMerchantDto merchant,
    String menuUrl
) {
    
}
