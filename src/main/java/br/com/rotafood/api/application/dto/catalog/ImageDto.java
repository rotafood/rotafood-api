package br.com.rotafood.api.application.dto.catalog;

import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.Image;

public record ImageDto(
    UUID id,
    String path,
    UUID iFoodImageId,
    String iFoodImagePath
) {

    public ImageDto(Image image, String bucketUrl, String bucketName) {
        this(
            image.getId(),
            generateFullUrl(image.getPath(), bucketUrl, bucketName),
            image.getIFoodImageId(),
            image.getIFoodImagePath()
        );
    }

    private static String generateFullUrl(String path, String bucketUrl, String bucketName) {
        return bucketUrl + "/" + bucketName + "/" + path;
    }
}
