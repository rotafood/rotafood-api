package br.com.rotafood.api.application.dto.logistic;

import java.util.Date;
import java.util.UUID;

import br.com.rotafood.api.application.dto.address.AddressDto;

public record VrpOrderDto (
    UUID id,
    double volumeLiters,
    Date createdAt,
    AddressDto address
) {}
