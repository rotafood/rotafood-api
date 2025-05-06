package br.com.rotafood.api.merchant.application.controller.v1;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.rotafood.api.infra.email.EmailService;
import br.com.rotafood.api.infra.security.TokenService;
import br.com.rotafood.api.infra.security.dtos.LoginDto;
import br.com.rotafood.api.infra.security.dtos.TokenJwtDto;
import br.com.rotafood.api.merchant.application.dto.MerchantOwnerCreationDto;
import br.com.rotafood.api.merchant.application.service.MerchantService;
import br.com.rotafood.api.merchant.domain.entity.MerchantUser;
import jakarta.validation.Valid;

@RestController
@RequestMapping( ApiVersion.VERSION + "/auth")
public class AuthController {

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmailService emailService;


    @PostMapping("/sigin") 
    @Transactional
    public ResponseEntity<TokenJwtDto> createMerchant(@RequestBody @Valid MerchantOwnerCreationDto merchantOwnerCreationDto) throws JsonProcessingException, IllegalArgumentException {
        MerchantUser merchantUser = merchantService.createMerchant(merchantOwnerCreationDto);
        var tokenJwtDto = tokenService.generateToken(merchantUser);
        return ResponseEntity.ok().body(tokenJwtDto);
    }

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<TokenJwtDto> login(@RequestBody @Valid LoginDto loginDto) throws JsonProcessingException, IllegalArgumentException {
        new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password());

        var merchantUser = merchantService.getMerchantUserByEmail(loginDto.email());
        var tokenJwtDto = tokenService.generateToken(merchantUser);
        
        return ResponseEntity.ok().body(tokenJwtDto);
    }



    @PostMapping("/sendTestEmail")
    public ResponseEntity<Void> sendTestEmail() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("username", "Vinicius");
        variables.put("rotafoodLink", "https://meusite-rotafood.com/acesso");

        emailService.sendEmail(
            "vinicostagandolfi@gmail.com", 
            "Teste - Sua Rotafood está disponível", 
            "route_created", 
            variables
        );

        return ResponseEntity.ok().build();
    }

 

}
