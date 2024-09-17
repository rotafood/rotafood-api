package br.com.rotafood.api.dto.logistic;

import java.util.List;
import java.util.UUID;

public record VrpRouteDto (
    UUID id,
    List<Integer> sequence,
    List<VrpOrderDto> orders,
    List<CoordinateDto> routeLine,
    double distanceKm,
    double volumeLiters,
    String linkGoogleMaps
) {}
