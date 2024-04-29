package br.com.rotafood.api.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rotafood.api.domain.merchant.dtos.CreateMerchantDto;
import br.com.rotafood.api.domain.merchant.models.MerchantUser;
import br.com.rotafood.api.domain.merchant.services.MerchantService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    @Autowired
    private MerchantService merchantService;

    @PostMapping("/merchants")
    @Transactional
    public ResponseEntity<MerchantUser> createMerchant(@RequestBody @Valid CreateMerchantDto createMerchantDto) {
            MerchantUser createdMerchantUser = merchantService.createMerchant(createMerchantDto);
            return ResponseEntity.ok(createdMerchantUser);
    }

}
