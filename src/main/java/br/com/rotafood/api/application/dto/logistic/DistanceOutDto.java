package br.com.rotafood.api.application.dto.logistic;

import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.application.dto.address.AddressDto;

public record DistanceOutDto(
    UUID id,
    AddressDto origin,
    AddressDto destiny,
    List<CoordinateDto> routeLine,
    Double distanceMeters,
    Double price
) { }
