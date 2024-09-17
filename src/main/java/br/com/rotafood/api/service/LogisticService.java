package br.com.rotafood.api.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.rotafood.api.dto.address.AddressDto;
import br.com.rotafood.api.dto.logistic.CoordinateDto;
import br.com.rotafood.api.dto.logistic.VrpInDto;
import br.com.rotafood.api.dto.logistic.VrpOrderDto;
import br.com.rotafood.api.dto.logistic.VrpOriginDto;
import br.com.rotafood.api.dto.logistic.VrpOutDto;


@Service
public class LogisticService {

    @Value("${ROTAFOOD_MS_LOGISTIC_URL}")
    private String logisticServiceUrl;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<VrpOutDto> logisticRoutesTest(VrpOriginDto origin, int pointsQuantity) {
        VrpInDto vrpData = generateDataForTest(origin, pointsQuantity, 0.005f);
        String url = this.logisticServiceUrl + "/logistic/cvrp/";
        return restTemplate.postForEntity(url, vrpData, VrpOutDto.class);
    }

    public VrpInDto generateDataForTest(VrpOriginDto origin, int pointsQuantity, float std) {
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
                coord.latitude(),
                coord.longitude()
            );

            var order = new VrpOrderDto(
                UUID.randomUUID(), 
                this.generateRandomVolume(1.0, 10.0),
                new java.util.Date(),
                randomAddress
            );

            orders.add(order);
        }

        return new VrpInDto(UUID.randomUUID(), origin, orders, 100.0f, 10.0f);
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
}
