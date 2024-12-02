package br.com.rotafood.api.application.controller.v1;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rotafood.api.application.dto.catalog.CatalogDto;
import br.com.rotafood.api.application.service.CatalogService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/merchants/{merchantId}/catalogs")
public class CatalogController {

    @Autowired
    public CatalogService catalogService;

    @GetMapping
    public List<CatalogDto> getAll(
        @PathVariable UUID merchantId
    ) {
        return this.catalogService.getAllByMerchantId(merchantId);
    }

    @GetMapping("/{catalogId}")
    public CatalogDto getById(
        @PathVariable UUID merchantId,
        @PathVariable UUID catalogId
    ) {
        return this.catalogService.getByIdAndMerchantId(catalogId, merchantId);
    }

    @PostMapping
    public CatalogDto create(
        @PathVariable UUID merchantId,
        @RequestBody @Valid CatalogDto catalogDto
    ) {
        return this.catalogService.createCatalog(catalogDto, merchantId);
    }

    @PutMapping("/{catalogId}")
    public CatalogDto update(
        @PathVariable UUID merchantId,
        @PathVariable UUID catalogId,
        @RequestBody @Valid CatalogDto catalogDto
    ) {
        return this.catalogService.updateCatalog(catalogDto, merchantId);
    }
    
}
