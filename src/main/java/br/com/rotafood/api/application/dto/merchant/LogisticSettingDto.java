package br.com.rotafood.api.application.dto.merchant;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.logistic.LogisticSetting;


public record LogisticSettingDto(
    UUID id,
    BigDecimal minTax,
    BigDecimal taxPerKm,
    BigDecimal kmRadius,
    UUID merchantId

) {

    public LogisticSettingDto(LogisticSetting logisticSetting) {
        this(
            logisticSetting.getId(), 
            logisticSetting.getMinTax(), 
            logisticSetting.getTaxPerKm(), 
            logisticSetting.getKmRadius(), 
            logisticSetting.getMerchant().getId()
            );
    }
    
}
