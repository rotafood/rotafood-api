package br.com.rotafood.api.application.dto.address;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record AddressDto (
    UUID id,

    
    String country,
    
    String state,
    
    String city,
    
    String neighborhood,
    
    String postalCode,
    
    String streetName,
    @NotNull
    String streetNumber,
    
    String formattedAddress,
    
    String complement,
    @NotNull
    BigDecimal latitude,
    @NotNull
    BigDecimal longitude
) {}
