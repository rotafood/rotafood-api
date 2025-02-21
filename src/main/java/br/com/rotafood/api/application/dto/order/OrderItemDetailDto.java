package br.com.rotafood.api.application.dto.order;

import java.util.UUID;

import br.com.rotafood.api.application.dto.catalog.ContextModifierDto;
import br.com.rotafood.api.domain.entity.catalog.CatalogContext;
import br.com.rotafood.api.domain.entity.catalog.Item;
import br.com.rotafood.api.domain.entity.catalog.Serving;

public record OrderItemDetailDto(
    UUID id,
    String name,
    String description,
    ContextModifierDto contextModifier,
    String ean,
    String additionalInformation,
    Serving serving,
    String imagePath
) {
    public OrderItemDetailDto(Item itemDetail, CatalogContext catalogContext) {
        this(
            itemDetail.getId(),
            itemDetail.getProduct().getName(),
            itemDetail.getProduct().getDescription(),
            itemDetail.getContextModifiers().stream()
                .filter(cm -> cm.getCatalogContext().equals(catalogContext))
                .findFirst()
                .map(ContextModifierDto::new)  
                .orElse(null),
            itemDetail.getProduct().getEan(),
            itemDetail.getProduct().getAdditionalInformation(),
            itemDetail.getProduct().getServing(),
            itemDetail.getProduct().getImagePath()
        );
    }
}
