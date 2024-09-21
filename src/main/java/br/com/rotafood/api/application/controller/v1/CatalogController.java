package br.com.rotafood.api.application.controller.v1;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rotafood.api.application.dto.catalog.CatalogDto;
import br.com.rotafood.api.application.dto.catalog.CatalogUpdateDto;
import br.com.rotafood.api.domain.entity.merchant.MerchantUser;
import br.com.rotafood.api.domain.repository.CatalogRepository;

@RestController
@RequestMapping("/v1/catalogs")
public class CatalogController {

    @Autowired
    public CatalogRepository catalogRepository;

    @GetMapping
    public CatalogDto getAll(
        @AuthenticationPrincipal 
        MerchantUser merchantUser
    ) {
        return null;

    }

    @GetMapping("/{id}")
    public CatalogDto getById(
        @PathVariable UUID id,
        @AuthenticationPrincipal 
        MerchantUser merchantUser
    ) {

        return null;

    }

    @PutMapping
    public CatalogDto updateById(
        @AuthenticationPrincipal 
        MerchantUser merchantUser,
        CatalogUpdateDto catalogUpdateDto
        ) {
        return null;
    }
    
}
