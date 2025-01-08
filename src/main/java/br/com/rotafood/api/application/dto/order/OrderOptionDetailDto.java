package br.com.rotafood.api.application.dto.order;


import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.Option;
import br.com.rotafood.api.domain.entity.catalog.Serving;

public record OrderOptionDetailDto(
    UUID id,
    String name,
    String description,
    String ean,
    String additionalInformation,
    Serving serving,
    String imagePath,
    Integer quantity,
    String optionGroupName,
    UUID optionGroupId
) {
    public OrderOptionDetailDto(Option option) {
        this(
            option.getId(),
            option.getProduct() != null ? option.getProduct().getName() : null,
            option.getProduct() != null ? option.getProduct().getDescription() : null,
            option.getProduct() != null ? option.getProduct().getEan() : null,
            option.getProduct() != null ? option.getProduct().getAdditionalInformation() : null,
            option.getProduct() != null ? option.getProduct().getServing() : null,
            option.getProduct() != null ? option.getProduct().getImagePath() : null,
            option.getFractions() != null ? option.getFractions().size() : null,
            option.getOptionGroup() != null ? option.getOptionGroup().getName() : null,
            option.getOptionGroup() != null ? option.getOptionGroup().getId() : null
        );
    }
    
}
