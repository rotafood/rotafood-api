package br.com.rotafood.api.application.controller.v1;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.rotafood.api.application.dto.catalog.FullCategoryDto;
import br.com.rotafood.api.application.dto.catalog.MerchantAndCategories;
import br.com.rotafood.api.application.dto.merchant.MerchantDto;
import br.com.rotafood.api.application.service.catalog.CategoryService;
import br.com.rotafood.api.domain.repository.MerchantRepository;

@RestController
@RequestMapping( ApiVersion.VERSION + "/catalogs")
public class CatalogOnline {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MerchantRepository merchantRepository;

    @GetMapping("/{onlineName}")
    public MerchantAndCategories getAllDelivery(
        @PathVariable String onlineName
    ) {
        var merchant = new MerchantDto(this.merchantRepository.findByOnlineName(onlineName)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Merchant not found")));
        
        var categories = categoryService.getAllByMerchantId(merchant.id()).stream()
            .map(FullCategoryDto::new)
            .toList();

        return new MerchantAndCategories(merchant, categories);
    }



}
