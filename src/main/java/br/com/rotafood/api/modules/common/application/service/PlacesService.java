package br.com.rotafood.api.modules.common.application.service;

import br.com.rotafood.api.modules.common.application.dto.AddressDto;
import br.com.rotafood.api.modules.common.domain.entity.Address;
import br.com.rotafood.api.modules.common.domain.repository.AddressRepository;
import br.com.rotafood.api.modules.order.application.dto.BrasilApiResponse;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.ComponentFilter;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
            throw new RuntimeException("CEP não encontrado.") ;
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
        if (response.location() != null && response.location().latitude() != null && response.location().longitude() != null) {
            latitude = response.location().latitude();
            longitude = response.location().longitude();
        }

        address.setLatitude(response.location().latitude());
        address.setLongitude(response.location().longitude());


        address.setLatitude(latitude);
        address.setLongitude(longitude);

        return new AddressDto(addressRepository.save(address));
    }

    private String defaultValue(String value) {
        return value == null ? "" : value;
    }

    public List<AddressDto> searchByAddress(String q) {
        List<Address> localResults = addressRepository.findByFormattedAddressContainingIgnoreCase(q);

        if (localResults.size() >= 3) {
            return localResults.stream()
                    .map(AddressDto::new)
                    .toList();
        }

        GeocodingResult[] googleResults;
        try {
            googleResults = GeocodingApi.newRequest(geoApiContext)
                .address(q)
                .components(
                        ComponentFilter.country("BR"),
                        ComponentFilter.administrativeArea("SP"),
                        ComponentFilter.locality("Limeira")
                )
                .await();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao chamar Geocoding API do Google", e);
        }

        return Arrays.stream(googleResults)
                .map(AddressDto::new)
                .map(addressDto -> {
                    var address = new Address(addressDto);
                    this.addressRepository.save(address);
                    return new AddressDto(address);
                })

                .collect(Collectors.toList());
    }

    @Transactional
    public AddressDto reverseGeocode(BigDecimal latitude, BigDecimal longitude) {
        try {
            LatLng location = new LatLng(latitude.doubleValue(), longitude.doubleValue());
            GeocodingResult[] results = GeocodingApi.reverseGeocode(geoApiContext, location).await();

            if (results == null || results.length == 0) {
                throw new RuntimeException("Endereço não encontrado para as coordenadas fornecidas.");
            }
            GeocodingResult firstResult = results[0];

            Address newAddress = new Address(new AddressDto(firstResult));
            return new AddressDto(addressRepository.save(newAddress));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar geocoding reverso: " + e.getMessage(), e);
        }
    }



}
