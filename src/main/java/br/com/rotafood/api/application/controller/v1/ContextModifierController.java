package br.com.rotafood.api.application.controller.v1;

import br.com.rotafood.api.application.dto.catalog.ContextModifierDto;
import br.com.rotafood.api.application.service.catalog.CatalogCacheService;
import br.com.rotafood.api.application.service.catalog.ContextModifierService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping( ApiVersion.VERSION + "/merchants/{merchantId}/contextModifiers")
public class ContextModifierController {

    @Autowired
    private ContextModifierService contextModifierService;

    @Autowired 
    private CatalogCacheService catalogCacheService;

    @PutMapping
    public ContextModifierDto updateOrCreate(
            @PathVariable UUID merchantId,
            @RequestBody @Valid ContextModifierDto contextModifierDto) {
        var cm = new ContextModifierDto(contextModifierService.updateOrCreate(contextModifierDto, null, null, null));
        catalogCacheService.updateCatalogCache(merchantId);
        return cm;
    }

}
