package br.com.rotafood.api.application.controller.v1;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.rotafood.api.application.dto.catalog.FullCategoryDto;
import br.com.rotafood.api.application.dto.catalog.MerchantAndCategories;
import br.com.rotafood.api.application.dto.merchant.FullMerchantDto;
import br.com.rotafood.api.application.dto.order.FullOrderDto;
import br.com.rotafood.api.application.service.catalog.CategoryService;
import br.com.rotafood.api.application.service.order.OrderService;
import br.com.rotafood.api.domain.repository.MerchantRepository;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping( ApiVersion.VERSION + "/catalogs/online")
public class CatalogOnlineController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private OrderService orderService;

    @GetMapping("/{onlineName}")
    public FullMerchantDto getMerchant(
        @PathVariable String onlineName
    ) {
        return new FullMerchantDto(this.merchantRepository.findByOnlineName(onlineName)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Merchant not found")));
    }


    @GetMapping("/{onlineName}/categories")
    public MerchantAndCategories getAllDelivery(
        @PathVariable String onlineName
    ) {
        var merchant = new FullMerchantDto(this.merchantRepository.findByOnlineName(onlineName)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Merchant not found")));
        
        var categories = categoryService.getAllByMerchantId(merchant.id()).stream()
            .map(FullCategoryDto::new)
            .toList();

        return new MerchantAndCategories(merchant, categories);
    }

    @GetMapping("/{onlineName}/orders/{orderId}")
    public FullOrderDto getOrderById(@PathVariable String onlineName, @PathVariable UUID orderId) {
        var merchant = new FullMerchantDto(this.merchantRepository.findByOnlineName(onlineName)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Merchant not found")));
        return new FullOrderDto(orderService.getByIdAndMerchantId(orderId, merchant.id()));
    }

    @PutMapping("/{onlineName}/orders")
    public FullOrderDto createOrUpdateOrder(
        @PathVariable String onlineName, 
        @RequestBody @Valid FullOrderDto orderDto
    ) {
        return new FullOrderDto(this.orderService.createOrUpdate(orderDto, orderDto.merchantId()));
    }
    
 
}
