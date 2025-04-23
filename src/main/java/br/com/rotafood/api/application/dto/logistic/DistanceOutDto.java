package br.com.rotafood.api.application.dto.logistic;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.application.dto.address.AddressDto;
import jakarta.validation.constraints.NotNull;

public record DistanceOutDto(
    UUID id,
    @NotNull
    AddressDto origin,
    @NotNull
    AddressDto destiny,
    @NotNull
    List<CoordinateDto> routeLine,
    @NotNull
    BigDecimal distanceMeters
) { }
