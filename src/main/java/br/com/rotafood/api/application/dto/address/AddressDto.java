package br.com.rotafood.api.application.dto.address;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record AddressDto (
    UUID id,

    @NotBlank
    String country,
    
    @NotBlank
    String state,
    
    @NotBlank
    String city,
    
    @NotBlank
    String neighborhood,
    
    @NotBlank
    String postalCode,
    
    @NotBlank
    String streetName,

    @NotNull
    String streetNumber,
    
    @NotNull
    String formattedAddress,
    
    String complement,

    @NotNull
    BigDecimal latitude,
    
    @NotNull
    BigDecimal longitude
) {}
