package br.com.rotafood.api.application.dto.merchant;

import java.util.UUID;

import br.com.rotafood.api.domain.entity.merchant.MerchantOrderEstimate;

public record MerchantOrderEstimateDto(
    UUID id,
    Integer pickupMinMinutes,
    Integer pickupMaxMinutes,
    Integer deliveryMinMinutes,
    Integer deliveryMaxMinutes,
    UUID merchantId
) {
    public MerchantOrderEstimateDto(MerchantOrderEstimate estimate) {
        this(
            estimate.getId(),
            estimate.getPickupMinMinutes(),
            estimate.getPickupMaxMinutes(),
            estimate.getDeliveryMinMinutes(),
            estimate.getDeliveryMaxMinutes(),
            estimate.getMerchant().getId()
        );
    }
}
