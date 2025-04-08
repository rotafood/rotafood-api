package br.com.rotafood.api.application.dto.merchant;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.logistic.MerchantLogisticSetting;


public record MerchantLogisticSettingDto(
    UUID id,
    BigDecimal minDeliveryFee,
    BigDecimal deliveryFeePerKm,
    BigDecimal maxDeliveryRadiusKm,
    BigDecimal freeDeliveryRadiusKm

) {

    public MerchantLogisticSettingDto(MerchantLogisticSetting logisticSetting) {
        this(
            logisticSetting.getId(), 
            logisticSetting.getMinDeliveryFee(), 
            logisticSetting.getDeliveryFeePerKm(), 
            logisticSetting.getMaxDeliveryRadiusKm(),
            logisticSetting.getFreeDeliveryRadiusKm()
            );
    }
    
}
