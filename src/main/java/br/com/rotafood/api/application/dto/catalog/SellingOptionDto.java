package br.com.rotafood.api.application.dto.catalog;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.SellingOption;

public record SellingOptionDto(
    UUID id,
    BigDecimal minimum,            
    BigDecimal incremental,       
    List<String> availableUnits,
    BigDecimal averageUnit        
) {
    public SellingOptionDto(SellingOption sellingOption) {
        this(
            sellingOption.getId(),
            sellingOption.getMinimum(),
            sellingOption.getIncremental(),
            sellingOption.getAvailableUnits(),
            sellingOption.getAverageUnit()
        );
    }
}