package br.com.rotafood.api.dto.logistic;

import java.util.UUID;

import br.com.rotafood.api.dto.address.AddressDto;

public  record VrpOriginDto (
    UUID id,
    AddressDto address
) {}
