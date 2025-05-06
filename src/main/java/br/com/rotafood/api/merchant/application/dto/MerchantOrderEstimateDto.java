package br.com.rotafood.api.merchant.application.dto;

import java.util.UUID;

import br.com.rotafood.api.merchant.domain.entity.MerchantOrderEstimate;

public record MerchantOrderEstimateDto(
    UUID id,
    Integer pickupMinMinutes,
    Integer pickupMaxMinutes,
    Integer deliveryMinMinutes,
    Integer deliveryMaxMinutes
) {
    public MerchantOrderEstimateDto(MerchantOrderEstimate estimate) {
        this(
            estimate.getId(),
            estimate.getPickupMinMinutes(),
            estimate.getPickupMaxMinutes(),
            estimate.getDeliveryMinMinutes(),
            estimate.getDeliveryMaxMinutes()
        );
    }
}
