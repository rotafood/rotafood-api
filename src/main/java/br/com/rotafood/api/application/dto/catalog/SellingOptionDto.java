package br.com.rotafood.api.application.dto.catalog;

import java.math.BigDecimal;
import java.util.List;

import br.com.rotafood.api.domain.entity.catalog.SellingOption;

public record SellingOptionDto(
    BigDecimal minimum,            
    BigDecimal incremental,       
    List<String> availableUnits,
    BigDecimal averageUnit        
) {
    public SellingOptionDto(SellingOption sellingOption) {
        this(
            sellingOption.getMinimum(),
            sellingOption.getIncremental(),
            sellingOption.getAvailableUnits(),
            sellingOption.getAverageUnit()
        );
    }
}
