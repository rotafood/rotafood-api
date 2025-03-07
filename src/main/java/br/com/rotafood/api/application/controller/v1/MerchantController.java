package br.com.rotafood.api.application.controller.v1;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rotafood.api.application.dto.merchant.FullMerchantDto;
import br.com.rotafood.api.application.service.merchant.MerchantService;
import jakarta.validation.Valid;

@RestController
@RequestMapping( ApiVersion.VERSION + "/merchants/{merchantId}")
public class MerchantController {
    @Autowired
    private MerchantService merchantService;

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
}
