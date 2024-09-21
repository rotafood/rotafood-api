package br.com.rotafood.api.aplication.dto.logistic;

import java.util.List;
import java.util.UUID;


public record VrpInDto (
    UUID id,
    VrpOriginDto origin,
    List<VrpOrderDto> orders, 
    float maxRouteVolume,
    int maxRouteOrders 
) {
    
}
