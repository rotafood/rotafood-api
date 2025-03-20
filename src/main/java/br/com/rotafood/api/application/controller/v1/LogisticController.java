package br.com.rotafood.api.application.controller.v1;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rotafood.api.application.dto.merchant.LogisticSettingDto;
import br.com.rotafood.api.application.dto.merchant.MerchantOrderEstimateDto;
import br.com.rotafood.api.application.service.merchant.LogisticSettingService;
import br.com.rotafood.api.application.service.merchant.MerchantOrderEstimateService;
import jakarta.validation.Valid;

@RestController
@RequestMapping( ApiVersion.VERSION + "/merchants/{merchantId}/logistic")
public class LogisticController {


    @Autowired
    private MerchantOrderEstimateService merchantOrderEstimateService;

    @Autowired
    private LogisticSettingService logisticSettingService;



    @GetMapping("/order-estimate")
    public MerchantOrderEstimateDto getOrderEstimate(@PathVariable UUID merchantId) {
        return new MerchantOrderEstimateDto(merchantOrderEstimateService.getByMerchantId(merchantId));
    }


    @PutMapping("/order-estimate")
    public MerchantOrderEstimateDto updateOrderEstimate(
            @PathVariable UUID merchantId,
            @RequestBody @Valid MerchantOrderEstimateDto estimateDto) {
        return new MerchantOrderEstimateDto(merchantOrderEstimateService.createOrUpdateEstimate(estimateDto, merchantId));
    }


    @GetMapping("/settings")
    public LogisticSettingDto getLogisticSettings(@PathVariable UUID merchantId) {
        return new LogisticSettingDto(logisticSettingService.getByMerchantId(merchantId));
    }

    @PutMapping("/settings")
    public LogisticSettingDto updateLogisticSettings(
            @PathVariable UUID merchantId,
            @RequestBody @Valid LogisticSettingDto logisticDto) {
        return new LogisticSettingDto(logisticSettingService.createOrUpdateSettings(logisticDto, merchantId));
    }

}
