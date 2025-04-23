package br.com.rotafood.api.application.controller.v1;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.rotafood.api.application.dto.address.AddressDto;
import br.com.rotafood.api.application.dto.catalog.MerchantAndMenuUrlDto;
import br.com.rotafood.api.application.dto.logistic.RouteDto;
import br.com.rotafood.api.application.dto.merchant.FullMerchantDto;
import br.com.rotafood.api.application.dto.order.FullOrderDto;
import br.com.rotafood.api.application.service.logistic.LogisticService;
import br.com.rotafood.api.application.service.order.OrderService;
import br.com.rotafood.api.domain.repository.MerchantRepository;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping( ApiVersion.VERSION + "/catalogs/online")
public class CatalogOnlineController {

    @Value("${minio.url}")
    private String minioUrl;

    @Value("${minio.bucket.name}")
    private String minioBucketName;

    @Autowired
    private LogisticService logisticService;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private OrderService orderService;

    @GetMapping("/{onlineName}")
    public FullMerchantDto getMerchant(
        @PathVariable String onlineName
    ) {
        return new FullMerchantDto(this.merchantRepository.findByOnlineName(onlineName)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Merchant not found")));
    }


    @GetMapping("/{onlineName}/categories")
    public MerchantAndMenuUrlDto getAllDelivery(
        @PathVariable String onlineName
    ) {
        var merchant = new FullMerchantDto(this.merchantRepository.findByOnlineName(onlineName)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Merchant not found")));
        
        var menuUrl = this.minioUrl + "/" + this.minioBucketName + "/catalogs/" + merchant.id().toString() + ".json";

        return new MerchantAndMenuUrlDto(merchant, menuUrl);
    }

    @GetMapping("/{onlineName}/orders/{orderId}")
    public FullOrderDto getOrderById(@PathVariable String onlineName, @PathVariable UUID orderId) {
        var merchant = new FullMerchantDto(this.merchantRepository.findByOnlineName(onlineName)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Merchant not found")));

        return new FullOrderDto(orderService.getByIdAndMerchantId(orderId, merchant.id()));
    }

    @PutMapping("/{onlineName}/orders")
    public FullOrderDto createOrUpdateOrder(
        @PathVariable String onlineName, 
        @RequestBody @Valid FullOrderDto orderDto
    ) {
        return new FullOrderDto(this.orderService.createOrUpdate(orderDto, orderDto.merchantId()));
    }

    @PostMapping("/{onlineName}/distances")
    public RouteDto getDistances(
            @PathVariable String onlineName, 
            @RequestBody @Valid AddressDto addressDto
    ) {
        var merchant = this.merchantRepository.findByOnlineName(onlineName)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Merchant not found"));

        var origin = new AddressDto(merchant.getAddress());

        RouteDto distance = logisticService.calculateDistance(origin, addressDto, merchant.getLogisticSetting());

        if (distance == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao calcular distância");
        }

        return distance;
    }
 
}
