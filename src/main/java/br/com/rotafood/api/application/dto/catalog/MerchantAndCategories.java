package br.com.rotafood.api.application.dto.catalog;

import java.util.List;

import br.com.rotafood.api.application.dto.merchant.FullMerchantDto;

public record MerchantAndCategories(
    FullMerchantDto merchant,
    List<FullCategoryDto> categories
) {
    
}
