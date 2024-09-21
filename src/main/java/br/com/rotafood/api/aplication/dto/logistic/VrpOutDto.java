package br.com.rotafood.api.aplication.dto.logistic;

import java.util.List;
import java.util.UUID;

public record VrpOutDto (
    UUID id,
    VrpOriginDto origin,
    List<VrpRouteDto> routes, 
    List<Integer> sequence,
    float maxRouteVolume,
    float maxRouteOrders
) {
    
}
