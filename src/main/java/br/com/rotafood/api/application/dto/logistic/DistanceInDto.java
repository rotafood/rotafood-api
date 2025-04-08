package br.com.rotafood.api.application.dto.logistic;

import java.util.UUID;

import br.com.rotafood.api.application.dto.AddressDto;

public record DistanceInDto(
    UUID id,
    AddressDto origin,
    AddressDto destiny
) { }

