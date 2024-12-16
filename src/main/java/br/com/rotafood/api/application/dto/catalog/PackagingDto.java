package br.com.rotafood.api.application.dto.catalog;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.Packaging;

public record PackagingDto(
    UUID id,
    String name,
    String imagePath,
    BigDecimal lenghtCm,
    BigDecimal widthCm,
    BigDecimal thicknessCm
) {
    public PackagingDto(Packaging packaging) {
        this(packaging.getId(), packaging.getName(), packaging.getImagePath(), packaging.getLenghtCm(), packaging.getWidthCm(), packaging.getThicknessCm());
    }
}