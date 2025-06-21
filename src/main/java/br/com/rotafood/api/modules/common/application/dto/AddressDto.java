package br.com.rotafood.api.modules.common.application.dto;

import br.com.rotafood.api.modules.common.domain.entity.Address;
import br.com.rotafood.api.modules.order.application.dto.BrasilApiResponse;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.GeocodingResult;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

public record AddressDto(
    UUID id,
    @NotBlank String country,
    @NotBlank String state,
    @NotBlank String city,
    @NotBlank String neighborhood,
    @NotBlank @Size(min = 8, max = 9, message = "O CEP deve ter entre 8 e 9 caracteres") String postalCode,
    @NotBlank String streetName,
    @NotNull String streetNumber,
    @NotNull String formattedAddress,
    String complement,
    @NotNull BigDecimal latitude,
    @NotNull BigDecimal longitude
) {
    public AddressDto(Address address) {
        this(
            address.getId(),
            address.getCountry(),
            address.getState(),
            address.getCity(),
            address.getNeighborhood(),
            address.getPostalCode(),
            address.getStreetName(),
            address.getStreetNumber(),
            address.getFormattedAddress(),
            address.getComplement(),
            address.getLatitude(),
            address.getLongitude()
        );
    }

    public AddressDto(BrasilApiResponse brasilApiResponse) {
        this(
            null,
            "Brasil",
            brasilApiResponse.state(),
            brasilApiResponse.city(),
            brasilApiResponse.neighborhood(),
            brasilApiResponse.cep(),
            brasilApiResponse.street(),
            "S/N",
            String.format("%s, %s, %s - %s",
                brasilApiResponse.street(),
                brasilApiResponse.neighborhood(),
                brasilApiResponse.city(),
                brasilApiResponse.state()
            ),
            "",
            brasilApiResponse.location() != null ? brasilApiResponse.location().latitude() : null,
            brasilApiResponse.location() != null ? brasilApiResponse.location().longitude() : null
        );
    }


    public AddressDto(GeocodingResult result) {
        this(
            null,
            "Brasil",
            extractComponent(result, AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_1),
            extractComponent(result, AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_2),
            getNeighborhoodFromResult(result),
            extractComponent(result, AddressComponentType.POSTAL_CODE),
            extractComponent(result, AddressComponentType.ROUTE),
            getStreetNumberFromResult(result),
            result.formattedAddress,
            "",
            BigDecimal.valueOf(result.geometry.location.lat),
            BigDecimal.valueOf(result.geometry.location.lng)
        );
    }

    private static String getNeighborhoodFromResult(GeocodingResult result) {
        String neighborhood = extractComponent(result, AddressComponentType.SUBLOCALITY_LEVEL_1);
        if (neighborhood.isBlank()) {
            return extractComponent(result, AddressComponentType.NEIGHBORHOOD);
        }
        return neighborhood;
    }

    private static String getStreetNumberFromResult(GeocodingResult result) {
        String streetNumber = extractComponent(result, AddressComponentType.STREET_NUMBER);
        return streetNumber.isBlank() ? "S/N" : streetNumber;
    }

    private static String extractComponent(GeocodingResult result, AddressComponentType type) {
        if (result == null || result.addressComponents == null) {
            return "";
        }
        return Arrays.stream(result.addressComponents)
                .filter(c -> c.types != null && Arrays.asList(c.types).contains(type))
                .findFirst()
                .map(c -> c.longName)
                .orElse("");
    }
}