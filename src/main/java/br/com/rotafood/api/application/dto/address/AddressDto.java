package br.com.rotafood.api.application.dto.address;

import java.math.BigDecimal;
import java.util.UUID;


public record AddressDto (
    UUID id,
    String country,
    String state,
    String city,
    String neighborhood,
    String postalCode,
    String streetName,
    String streetNumber,
    String formattedAddress,
    String complement,
    BigDecimal latitude,
    BigDecimal longitude
) {}
