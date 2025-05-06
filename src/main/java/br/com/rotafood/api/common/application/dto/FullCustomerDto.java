package br.com.rotafood.api.common.application.dto;

import br.com.rotafood.api.common.domain.entity.Customer;

import java.util.List;
import java.util.UUID;

public record FullCustomerDto(
    UUID id,
    String name,
    String phone,
    List<AddressDto> addresses
) {
    public FullCustomerDto(Customer customer) {
        this(
            customer.getId(),
            customer.getName(),
            customer.getPhone(),
            customer.getAddresses()
                .stream()
                .map(ca -> new AddressDto(ca.getAddress()))
                .toList()
        );
    }
}
