package br.com.rotafood.api.application.service;

import br.com.rotafood.api.application.dto.address.AddressDto;
import br.com.rotafood.api.application.dto.address.BrasilApiResponse;
import br.com.rotafood.api.domain.entity.address.Address;
import br.com.rotafood.api.domain.repository.AddressRepository;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;

@Service
public class PlacesService {

    private final AddressRepository addressRepository;
    private final GeoApiContext geoApiContext;
    private final RestTemplate restTemplate;

    public PlacesService(AddressRepository addressRepository, GeoApiContext geoApiContext, RestTemplate restTemplate) {
        this.addressRepository = addressRepository;
        this.geoApiContext = geoApiContext;
        this.restTemplate = restTemplate;
    }

    @Transactional
    public AddressDto findOrCreateByCep(String cep) {
        if (cep == null || !cep.matches("\\d{8,9}")) {
            throw new IllegalArgumentException("CEP inválido.");
        }

        return addressRepository.findByPostalCode(cep)
                .map(AddressDto::new)
                .orElseGet(() -> fetchFromApisAndSave(cep));
    }

    private AddressDto fetchFromApisAndSave(String cep) {
        BrasilApiResponse response = restTemplate.getForObject(
                "https://brasilapi.com.br/api/cep/v2/" + cep,
                BrasilApiResponse.class
        );

        if (response == null) {
            return fetchFromGoogle(cep); // fallback total
        }

        Address address = new Address();
        address.setPostalCode(response.cep());
        address.setState(response.state());
        address.setCity(response.city());
        address.setNeighborhood(response.neighborhood());
        address.setStreetName(response.street());
        address.setCountry("Brasil");
        address.setFormattedAddress(String.format(
                "%s - %s, %s - %s",
                defaultValue(response.street()),
                defaultValue(response.neighborhood()),
                defaultValue(response.city()),
                defaultValue(response.state())
        ));

        BigDecimal latitude = null;
        BigDecimal longitude = null;
        if (response.location() != null) {
            latitude = response.location().latitude();
            longitude = response.location().longitude();
        }

        if (latitude == null || longitude == null ||
            BigDecimal.ZERO.compareTo(latitude) == 0 || BigDecimal.ZERO.compareTo(longitude) == 0) {
            try {
                GeocodingResult[] results = GeocodingApi.geocode(geoApiContext, cep + ", Brasil").await();

                if (results.length > 0) {
                    LatLng location = results[0].geometry.location;
                    latitude = BigDecimal.valueOf(location.lat);
                    longitude = BigDecimal.valueOf(location.lng);

                    if (address.getFormattedAddress() == null || address.getFormattedAddress().isBlank()) {
                        address.setFormattedAddress(results[0].formattedAddress);
                    }
                }
            } catch (ApiException | InterruptedException | IOException e) {
                throw new RuntimeException("Erro ao acessar Google Maps API", e);
            }
        }

        address.setLatitude(latitude);
        address.setLongitude(longitude);

        return new AddressDto(addressRepository.save(address));
    }

    private String defaultValue(String value) {
        return value == null ? "" : value;
    }


    private AddressDto fetchFromGoogle(String cep) {
        try {
            GeocodingResult[] results = GeocodingApi.geocode(geoApiContext, cep + ", Brasil").await();

            if (results.length == 0) {
                throw new IllegalStateException("Nenhum resultado retornado do Google Maps API.");
            }

            LatLng location = results[0].geometry.location;

            Address address = new Address();
            address.setPostalCode(cep);
            address.setFormattedAddress(results[0].formattedAddress);
            address.setLatitude(BigDecimal.valueOf(location.lat));
            address.setLongitude(BigDecimal.valueOf(location.lng));
            address.setCountry("Brasil");

            return new AddressDto(addressRepository.save(address));
        } catch (ApiException | InterruptedException | IOException e) {
            throw new RuntimeException("Erro ao acessar Google Maps API", e);
        }
    }
}
