package br.com.rotafood.api.application.dto.catalog;

import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.Image;

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


