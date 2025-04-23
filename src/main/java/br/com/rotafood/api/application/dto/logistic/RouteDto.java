package br.com.rotafood.api.application.dto.logistic;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.application.dto.address.AddressDto;
import jakarta.validation.constraints.NotNull;

public record RouteDto(
    UUID id,
    @NotNull
    AddressDto origin,
    @NotNull
    AddressDto destiny,
    @NotNull
    BigDecimal distanceMeters,
    @NotNull
    BigDecimal deliveryFee,
    @NotNull
    List<CoordinateDto> routeLine
) { }
