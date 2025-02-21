package br.com.rotafood.api.application.dto.catalog;

import java.util.List;

import br.com.rotafood.api.application.dto.merchant.MerchantDto;

public record MerchantAndCategories(
    MerchantDto merchant,
    List<FullCategoryDto> categories
) {
    
}
