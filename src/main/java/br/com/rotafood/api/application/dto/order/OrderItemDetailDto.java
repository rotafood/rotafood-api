package br.com.rotafood.api.application.dto.order;


import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.application.dto.catalog.PriceDto;
import br.com.rotafood.api.application.dto.catalog.ProductPackagingDto;
import br.com.rotafood.api.domain.entity.catalog.Serving;

public record OrderItemDetailDto(
    UUID id,
    String name,
    String description,
    String ean,
    String additionalInformation,
    Serving serving,
    String imagePath,
    PriceDto itemPrice,
    List<PriceDto> optionsPrice,
    ProductPackagingDto packaging,
    List<OrderItemOptionDto> options
) {}
