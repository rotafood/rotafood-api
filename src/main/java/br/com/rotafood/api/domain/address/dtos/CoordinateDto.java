package br.com.rotafood.api.domain.address.dtos;

import java.util.Optional;
import java.util.UUID;

public record CoordinateDto (
    Optional<UUID> id,
    Double latitude,
    Double longitude
    ) {}
