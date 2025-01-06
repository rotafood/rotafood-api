package br.com.rotafood.api.application.dto.order;

import java.util.UUID;

public record OrderCustomerDto(
    UUID id,
    int ordersCountOnMerchant,
    String segmentation,
    String name,
    String document,
    String phone
) {}

