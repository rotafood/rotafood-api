package br.com.rotafood.api.logistic.application.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import br.com.rotafood.api.common.application.dto.AddressDto;

public record VrpOrderDto (
    UUID id,
    BigDecimal volumeLiters,
    Date createdAt,
    AddressDto address
) {}
