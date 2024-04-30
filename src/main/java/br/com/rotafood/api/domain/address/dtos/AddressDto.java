package br.com.rotafood.api.domain.address.dtos;

import java.math.BigDecimal;
import java.util.UUID;


public record AddressDto (
    UUID id,
    String country,
    String state,
    String city,
    String postalCode,
    String streetName,
    String streetNumber,
    String formattedAddress,
    BigDecimal latitude,
    BigDecimal longitude
) {}
