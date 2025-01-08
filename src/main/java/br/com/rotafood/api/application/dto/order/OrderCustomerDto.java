package br.com.rotafood.api.application.dto.order;

import java.util.UUID;

import br.com.rotafood.api.domain.entity.order.OrderCustomer;

public record OrderCustomerDto(
    UUID id,
    int ordersCountOnMerchant,
    String segmentation,
    String name,
    String document,
    String phone
) {
    public OrderCustomerDto(OrderCustomer customer) {
    this(
        customer.getId(),
        customer.getOrdersCountOnMerchant(),
        customer.getSegmentation(),
        customer.getName(),
        customer.getDocument(),
        customer.getPhone()
    );
}

}

