package br.com.rotafood.api.application.dto.order;


import java.util.UUID;

import br.com.rotafood.api.application.dto.catalog.ContextModifierDto;
import br.com.rotafood.api.domain.entity.catalog.CatalogContext;
import br.com.rotafood.api.domain.entity.catalog.Option;
import br.com.rotafood.api.domain.entity.catalog.Serving;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record OrderOptionDetailDto(
    @NotNull
    UUID id,
    @NotNull
    String name,
    String description,
    @Valid
    @NotNull
    ContextModifierDto contextModifier,
    String ean,
    String additionalInformation,
    Serving serving,
    String imagePath,
    Integer fractions
) {
    public OrderOptionDetailDto(Option option, CatalogContext catalogContext) {
        this(
            option.getId(),
            option.getProduct() != null ? option.getProduct().getName() : null,
            option.getProduct() != null ? option.getProduct().getDescription() : null,
            option.getContextModifiers().stream()
                .filter(cm -> cm.getCatalogContext().equals(catalogContext))
                .findFirst()
                .map(ContextModifierDto::new)  
                .orElse(null),
            option.getProduct() != null ? option.getProduct().getEan() : null,
            option.getProduct() != null ? option.getProduct().getAdditionalInformation() : null,
            option.getProduct() != null ? option.getProduct().getServing() : null,
            option.getProduct() != null ? option.getProduct().getImagePath() : null,
            option.getFractions() != null ? option.getFractions().size() : null
        );
    }
    
}
