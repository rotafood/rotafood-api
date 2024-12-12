package br.com.rotafood.api.application.dto.catalog;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.Packaging;

public record PackagingDto(
    UUID id,
    String name,
    BigDecimal lenghtCm,
    BigDecimal widthCm,
    BigDecimal thicknessCm
) {
    public PackagingDto(Packaging packaging) {
        this(packaging.getId(), packaging.getName(), packaging.getLenghtCm(), packaging.getWidthCm(), packaging.getThicknessCm());
    }
}