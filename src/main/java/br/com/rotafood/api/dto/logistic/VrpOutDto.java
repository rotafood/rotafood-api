package br.com.rotafood.api.dto.logistic;

import java.util.List;
import java.util.UUID;


public record VrpOutDto (
    UUID id,
    VrpOriginDto origin,
    List<VrpOrderDto> orders, 
    List<Integer> sequence,
    List<CoordinateDto> routeLine,
    String linkGoogleMaps,
    float maxRouteVolume,
    float maxRouteOrders,
    double distanceMeters, 
    double volumeLiters
) {
    
}
