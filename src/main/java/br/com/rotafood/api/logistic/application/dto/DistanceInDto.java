package br.com.rotafood.api.logistic.application.dto;

import java.util.UUID;

import br.com.rotafood.api.common.application.dto.AddressDto;

public record DistanceInDto(
    UUID id,
    AddressDto origin,
    AddressDto destiny
) { }

