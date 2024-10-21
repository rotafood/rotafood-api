package br.com.rotafood.api.application.dto.logistic;

import java.util.Date;
import java.util.List;
import java.util.UUID;


public record VrpInDto (
    UUID id,
    VrpOriginDto origin,
    List<VrpOrderDto> orders, 
    float maxRouteVolume,
    Date createdAt
) {
    
}
