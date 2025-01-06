package br.com.rotafood.api.application.dto.order;


import java.util.UUID;

import br.com.rotafood.api.application.dto.catalog.PriceDto;
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
    UUID optionGroupId,
    PriceDto price
) {}
