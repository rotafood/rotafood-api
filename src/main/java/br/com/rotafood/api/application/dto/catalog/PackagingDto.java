package br.com.rotafood.api.application.dto.catalog;

import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.Packaging;

public record PackagingDto(
    UUID id,
    String name,
    Double lenghtCm,
    String widthCm,
    Double thicknessCm
) {
    public PackagingDto(Packaging packaging) {
        this(packaging.getId(), packaging.getName(), packaging.getLenghtCm(), packaging.getWidthCm(), packaging.getThicknessCm());
    }
}