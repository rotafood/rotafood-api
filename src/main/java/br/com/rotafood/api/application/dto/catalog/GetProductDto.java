package br.com.rotafood.api.application.dto.catalog;

import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.DietaryRestrictions;
import br.com.rotafood.api.domain.entity.catalog.Serving;

public record GetProductDto(
    UUID id,

    String name,
    String description,
    String ean,
    Serving serving,
    String additionalInformation,
    WeightDto weight,
    SellingOptionDto sellingOption,
    String image,
    List<String> tags,
    List<DietaryRestrictions> dietaryRestrictions,
    List<String> multipleImages
) {


}
