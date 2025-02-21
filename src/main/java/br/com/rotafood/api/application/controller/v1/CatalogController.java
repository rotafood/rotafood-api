package br.com.rotafood.api.application.controller.v1;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rotafood.api.application.dto.catalog.CatalogDto;
import br.com.rotafood.api.application.dto.merchant.MerchantUserDto;
import br.com.rotafood.api.application.service.catalog.CatalogService;
import br.com.rotafood.api.domain.entity.catalog.AvailabilityStatus;
import jakarta.validation.Valid;


@RestController
@RequestMapping( ApiVersion.VERSION + "/merchants/{merchantId}/catalogs")
public class CatalogController {

    @Autowired
    public CatalogService catalogService;

    @PreAuthorize("hasAuthority('CATEGORY')")
    @GetMapping
    public List<CatalogDto> getAll(
        @PathVariable UUID merchantId,
        @AuthenticationPrincipal
        MerchantUserDto merchantUserDto
    ) {
        return this.catalogService.getAllByMerchantId(merchantId);
    }

    @PreAuthorize("hasAuthority('CATEGORY')")
    @GetMapping("/{catalogId}")
    public CatalogDto getById(
        @PathVariable UUID merchantId,
        @PathVariable UUID catalogId
    ) { 
        return new CatalogDto(this.catalogService.getByIdAndMerchantId(catalogId, merchantId));
    }

    @PreAuthorize("hasAuthority('CATEGORY')")
    @GetMapping("/{catalogId}/categories")
    public CatalogDto getCategoriesById(
        @PathVariable UUID merchantId,
        @PathVariable UUID catalogId
    ) { 
        return new CatalogDto(this.catalogService.getByIdAndMerchantId(catalogId, merchantId));
    }



    @PreAuthorize("hasAuthority('CATEGORY')")
    @PutMapping
    public CatalogDto updateOrCreate(
        @PathVariable UUID merchantId,
        @PathVariable UUID catalogId,
        @RequestBody @Valid CatalogDto catalogDto
    ) {
        return new CatalogDto(this.catalogService.updateOrCreate(catalogDto, merchantId));
    }

    @PreAuthorize("hasAuthority('CATEGORY')")
    @PutMapping("/{catalogId}/{status}")
    public CatalogDto changeStatus(
        @PathVariable UUID merchantId,
        @PathVariable UUID catalogId,
        @PathVariable AvailabilityStatus status,
        @RequestBody @Valid CatalogDto catalogDto
    ) {
        return new CatalogDto(this.catalogService.updateOrCreate(catalogDto, merchantId));
    }
    
}
