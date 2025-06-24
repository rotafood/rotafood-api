package br.com.rotafood.api.modules.common.application.service;

import br.com.rotafood.api.modules.common.application.dto.AddressDto;
import br.com.rotafood.api.modules.common.application.mapper.AddressMapper; 
import br.com.rotafood.api.modules.common.domain.entity.Address;
import br.com.rotafood.api.modules.common.domain.repository.AddressRepository;
import br.com.rotafood.api.modules.order.application.dto.BrasilApiResponse;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.AddressType;
import com.google.maps.model.ComponentFilter;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlacesService {

    private final AddressRepository addressRepository;
    private final GeoApiContext geoApiContext;
    private final RestTemplate restTemplate;
    private final AddressMapper addressMapper; 

    public PlacesService(AddressRepository addressRepository, GeoApiContext geoApiContext, RestTemplate restTemplate, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.geoApiContext = geoApiContext;
        this.restTemplate = restTemplate;
        this.addressMapper = addressMapper;
    }

    @Transactional
    public AddressDto findOrCreateByCep(String cep) {
        if (cep == null || !cep.matches("\\d{8,9}")) {
            throw new IllegalArgumentException("CEP inválido.");
        }

        return addressRepository.findByPostalCode(cep)
                .map(addressMapper::toDto)
                .orElseGet(() -> fetchFromApisAndSave(cep));
    }

    private AddressDto fetchFromApisAndSave(String cep) {
        BrasilApiResponse response = restTemplate.getForObject(
                "https://brasilapi.com.br/api/cep/v2/" + cep,
                BrasilApiResponse.class);

        if (response == null) {
            throw new RuntimeException("CEP não encontrado.");
        }

        AddressDto dto = addressMapper.fromBrasilApiResponse(response);
        Address addressToSave = addressMapper.toEntity(dto);
        Address savedAddress = addressRepository.save(addressToSave);

        return addressMapper.toDto(savedAddress);
    }


    public List<AddressDto> searchByAddress(String q) {
        List<Address> localResults = addressRepository.findByFormattedAddressContainingIgnoreCase(q);

        if (localResults.size() >= 3) {
            return localResults.stream()
                    .map(addressMapper::toDto)
                    .toList();
        }

        GeocodingResult[] googleResults;
        try {
            googleResults = GeocodingApi.newRequest(geoApiContext)
                .address(q)
                .language("pt-BR")
                .components(ComponentFilter.country("BR"))
                .await();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao chamar Geocoding API do Google", e);
        }

        List<AddressDto> finalResults = new ArrayList<>();
        for (GeocodingResult result : googleResults) {
            AddressDto dto = addressMapper.fromGeocodingResult(result);
            Address entity = addressMapper.toEntity(dto);
            Address savedEntity = this.addressRepository.save(entity);
            finalResults.add(addressMapper.toDto(savedEntity));
        }
        return finalResults;
    }

    @Transactional
    public AddressDto reverseGeocode(BigDecimal latitude, BigDecimal longitude) {
        try {
            LatLng location = new LatLng(latitude.doubleValue(), longitude.doubleValue());

            GeocodingResult[] results = GeocodingApi.reverseGeocode(geoApiContext, location)
                    .language("pt-BR")
                    .resultType(AddressType.STREET_ADDRESS)
                    .await();

            if (results == null || results.length == 0) {
                throw new Exception("Não foi possível encontrar um endereço de rua para as coordenadas fornecidas.");
            }

            GeocodingResult candidate = results[0];

            if (!addressMapper.isResultComplete(candidate)) {
                throw new Exception(
                    "O endereço encontrado não possui todos os dados obrigatórios (Rua, Bairro, CEP, etc). " +
                    "Endereço retornado pela API: " + candidate.formattedAddress
                );
            }
            
            AddressDto dto = addressMapper.fromGeocodingResult(candidate);
            Address newAddress = addressMapper.toEntity(dto);
            Address savedAddress = addressRepository.save(newAddress);

            return addressMapper.toDto(savedAddress);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro inesperado durante o geocoding reverso: " + e.getMessage(), e);
        }
    }




}
