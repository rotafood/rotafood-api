package br.com.rotafood.api.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.rotafood.api.domain.merchant.MerchantUser;
import br.com.rotafood.api.dto.merchant.CreateMerchantDto;
import br.com.rotafood.api.infra.security.TokenService;
import br.com.rotafood.api.infra.security.dtos.LoginDto;
import br.com.rotafood.api.infra.security.dtos.TokenJwtDto;
import br.com.rotafood.api.service.MerchantService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;


    @PostMapping("/merchants")
    @Transactional
    public ResponseEntity<TokenJwtDto> createMerchant(@RequestBody @Valid CreateMerchantDto createMerchantDto) throws JsonProcessingException, IllegalArgumentException {
        MerchantUser merchantUser = merchantService.createMerchant(createMerchantDto);
        var tokenJwtDto = tokenService.generateToken((MerchantUser) merchantUser);
        return ResponseEntity.ok().body(tokenJwtDto);
    }

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<Object> login(@RequestBody @Valid LoginDto loginDto) throws JsonProcessingException, IllegalArgumentException {
        var token = new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password());
        var authentication = authManager.authenticate(token);
        var tokenJwtDto = tokenService.generateToken((MerchantUser) authentication.getPrincipal());
        
        return ResponseEntity.ok().body(tokenJwtDto);
    }

}
