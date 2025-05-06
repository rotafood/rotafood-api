package br.com.rotafood.api.catalog.application.dto;

import java.util.UUID;

import br.com.rotafood.api.merchant.domain.entity.Image;

public record ImageDto(
    UUID id,
    String path,
    UUID iFoodImageId,
    String iFoodImagePath
) {

    public ImageDto(Image image) {
        this(
            image.getId(),
            image.getPath(),
            image.getIFoodImageId(),
            image.getIFoodImagePath()
        );
    }
}


