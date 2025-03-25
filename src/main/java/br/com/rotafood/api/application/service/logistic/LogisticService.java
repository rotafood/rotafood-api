package br.com.rotafood.api.application.service.logistic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.rotafood.api.application.dto.address.AddressDto;
import br.com.rotafood.api.application.dto.logistic.CoordinateDto;
import br.com.rotafood.api.application.dto.logistic.DistanceInDto;
import br.com.rotafood.api.application.dto.logistic.DistanceOutDto;
import br.com.rotafood.api.application.dto.logistic.VrpInDto;
import br.com.rotafood.api.application.dto.logistic.VrpOrderDto;
import br.com.rotafood.api.application.dto.logistic.VrpOriginDto;
import br.com.rotafood.api.application.dto.logistic.VrpOutDto;


@Service
public class LogisticService {

    @Value("${ROTAFOOD_MS_LOGISTIC_URL}")
    private String logisticServiceUrl;

    @Autowired
    private RestTemplate restTemplate;

    public VrpOutDto logisticRoutesTest(VrpOriginDto origin, Long pointsQuantity) {
        VrpInDto vrpData = generateDataForTest(origin, pointsQuantity, 0.010f);
        String url = this.logisticServiceUrl + "/logistic/vrp";
        ResponseEntity<VrpOutDto> response = restTemplate.postForEntity(url, vrpData, VrpOutDto.class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        }

        throw new RuntimeException("Erro ao buscar distância do serviço logístico");
    }

    public VrpInDto generateDataForTest(VrpOriginDto origin, Long pointsQuantity, float std) {
        List<VrpOrderDto> orders = new ArrayList<>();

        for (int i = 0; i < pointsQuantity; i++) {
            var coord = generateRandomCoordinates(origin.address().latitude(), origin.address().longitude(), std);
            AddressDto randomAddress = new AddressDto(
                UUID.randomUUID(),
                origin.address().country(),
                origin.address().state(),
                origin.address().city(),
                origin.address().neighborhood(),
                origin.address().postalCode(),
                origin.address().streetName(),
                origin.address().streetNumber(),
                origin.address().formattedAddress(),
                origin.address().complement(),
                coord.lat(),
                coord.lng()
            );

            var order = new VrpOrderDto(
                UUID.randomUUID(), 
                this.generateRandomVolume(1.0, 10.0),
                new java.util.Date(),
                randomAddress
            );

            orders.add(order);
        }

        return new VrpInDto(UUID.randomUUID(), origin, orders, 45.0f, new Date(System.currentTimeMillis()));
    }

    private CoordinateDto generateRandomCoordinates(BigDecimal lat, BigDecimal lng, float std) {
        double randomLat = lat.doubleValue() + (ThreadLocalRandom.current().nextGaussian() * std);
        double randomLng = lng.doubleValue() + (ThreadLocalRandom.current().nextGaussian() * std);

        return new CoordinateDto(
            BigDecimal.valueOf(randomLat).setScale(6, RoundingMode.HALF_UP),
            BigDecimal.valueOf(randomLng).setScale(6, RoundingMode.HALF_UP)
        );
    }

    private double generateRandomVolume(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }


    public DistanceOutDto calculateDistance(AddressDto origin, AddressDto destiny) {
        DistanceInDto distanceIn = new DistanceInDto(UUID.randomUUID(), origin, destiny);
        String url = this.logisticServiceUrl + "/logistic/distances";

        ResponseEntity<DistanceOutDto> response = restTemplate.postForEntity(url, distanceIn, DistanceOutDto.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        }


        throw new RuntimeException("Erro ao buscar distância do serviço logístico");
    }

}
