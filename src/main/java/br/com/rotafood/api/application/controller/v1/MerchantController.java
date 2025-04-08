package br.com.rotafood.api.application.controller.v1;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.rotafood.api.application.dto.AddressDto;
import br.com.rotafood.api.application.dto.logistic.RouteDto;
import br.com.rotafood.api.application.dto.merchant.FullMerchantDto;
import br.com.rotafood.api.application.service.logistic.LogisticService;
import br.com.rotafood.api.application.service.merchant.MerchantService;
import jakarta.validation.Valid;

@RestController
@RequestMapping( ApiVersion.VERSION + "/merchants/{merchantId}")
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private LogisticService logisticService;

    @GetMapping
    public FullMerchantDto getMerchantById(@PathVariable UUID merchantId) {
        return new FullMerchantDto(merchantService.getMerchantById(merchantId));
    }

    @PutMapping
    public FullMerchantDto updateMerchantById(
            @PathVariable UUID merchantId,
            @RequestBody @Valid FullMerchantDto merchantDto) {

            return new FullMerchantDto(merchantService.updateMerchant(merchantDto));
    }

    @PostMapping("/distances")
    public RouteDto getDistances(
            @PathVariable UUID merchantId,
            @RequestBody @Valid AddressDto addressDto
    ) {
        var merchant = this.merchantService.getMerchantById(merchantId);

        var origin = new AddressDto(merchant.getAddress());

        RouteDto dto = logisticService.calculateDistance(origin, addressDto, merchant.getLogisticSetting());

        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao calcular distância");
        }

        return dto;
    }

}
