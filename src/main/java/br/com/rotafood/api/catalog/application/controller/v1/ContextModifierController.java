package br.com.rotafood.api.catalog.application.controller.v1;

import br.com.rotafood.api.catalog.application.dto.ContextModifierDto;
import br.com.rotafood.api.catalog.application.service.CatalogCacheService;
import br.com.rotafood.api.catalog.application.service.ContextModifierService;

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
        catalogCacheService.updateOrCreateContextModifier(merchantId, cm);
        return cm;
    }

}
