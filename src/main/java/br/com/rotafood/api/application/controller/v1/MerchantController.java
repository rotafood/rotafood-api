package br.com.rotafood.api.application.controller.v1;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rotafood.api.application.dto.merchant.MerchantDto;
import br.com.rotafood.api.application.service.merchant.MerchantService;
import jakarta.validation.Valid;

@RestController
@RequestMapping( ApiVersion.VERSION + "/merchants/{merchantId}")
public class MerchantController {
    @Autowired
    private MerchantService merchantService;

    @GetMapping
    public MerchantDto getMerchantById(@PathVariable UUID merchantId) {
        return new MerchantDto(merchantService.getMerchantById(merchantId));
    }

    @PutMapping
    public MerchantDto updateMerchantById(
            @PathVariable UUID merchantId,
            @RequestBody @Valid MerchantDto merchantDto) {

            return new MerchantDto(merchantService.updateMerchant(merchantDto));
        }
}
