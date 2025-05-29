package br.com.rotafood.api.modules.common.application.service;

import br.com.rotafood.api.modules.common.application.dto.AddressDto;
import br.com.rotafood.api.modules.common.domain.entity.Address;
import br.com.rotafood.api.modules.common.domain.repository.AddressRepository;
import br.com.rotafood.api.modules.order.application.dto.BrasilApiResponse;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.ComponentFilter;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

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
                BrasilApiResponse.class);

        if (response == null) {
            return fetchFromGoogle(cep);
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
                defaultValue(response.state())));

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

    @Transactional
    public List<AddressDto> searchByAddress(String q) {
        List<Address> local = addressRepository
                .findByFormattedAddressContainingIgnoreCase(q);
        if (!local.isEmpty()) {
            return local.stream().map(AddressDto::new).toList();
        }

        GeocodingResult[] results;
        try {
            results = GeocodingApi
                    .geocode(geoApiContext, q + ", Brasil")
                    .await();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao chamar Geocoding API do Google", e);
        }

        if (results.length == 0) {
            throw new IllegalStateException("Nenhum resultado retornado pelo Google.");
        }

        try {
            results = GeocodingApi.newRequest(geoApiContext)
            .address(q)
            .components(ComponentFilter.country("BR"), ComponentFilter.administrativeArea("SP"))              
            .await();
        } catch (Exception e) {
            throw new RuntimeException("Erro no Geocoding", e);
        }
        
        return Arrays.stream(results)
        .map(r -> {
            LatLng loc = r.geometry.location;


            Address a = new Address();
            a.setCountry("Brasil");
            a.setState(extractComponent(r, AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_1));
                a.setCity(extractComponent(r, AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_2));

                String nb = extractComponent(r, AddressComponentType.SUBLOCALITY_LEVEL_1);
                a.setNeighborhood(nb.isBlank()
                        ? extractComponent(r, AddressComponentType.NEIGHBORHOOD)
                        : nb);

                a.setPostalCode(extractComponent(r, AddressComponentType.POSTAL_CODE));
                a.setStreetName(extractComponent(r, AddressComponentType.ROUTE));

                String num = extractComponent(r, AddressComponentType.STREET_NUMBER);
                a.setStreetNumber(num.isBlank() ? "S/N" : num);

            a.setFormattedAddress(r.formattedAddress);
            a.setComplement("");
            a.setLatitude(BigDecimal.valueOf(loc.lat));
            a.setLongitude(BigDecimal.valueOf(loc.lng));

            Address saved = addressRepository.save(a);
            return new AddressDto(saved);
        })
        .toList();
  
    }

    private static String extractComponent(GeocodingResult r,
                                       AddressComponentType type) {  
    return Arrays.stream(r.addressComponents)
                    .filter(c -> c.types != null &&
                                Arrays.asList(c.types).contains(type))
                    .findFirst()
                    .map(c -> c.longName)      // ou .shortName se preferir
                    .orElse("");
    }
}
