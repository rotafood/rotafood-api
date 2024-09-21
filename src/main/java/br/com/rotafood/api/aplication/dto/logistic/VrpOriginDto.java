package br.com.rotafood.api.aplication.dto.logistic;

import java.util.UUID;

import br.com.rotafood.api.aplication.dto.address.AddressDto;

public  record VrpOriginDto (
    UUID id,
    AddressDto address
) {}
