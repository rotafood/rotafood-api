package br.com.rotafood.api.application.dto.customer;

import br.com.rotafood.api.domain.entity.customer.Customer;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CustomerDto(
    UUID id,
    @NotNull
    String name,
    @NotNull
    String phone
) {
    public CustomerDto(Customer customer) {
        this(
            customer.getId(),
            customer.getName(),
            customer.getPhone()
        );
    }
}
