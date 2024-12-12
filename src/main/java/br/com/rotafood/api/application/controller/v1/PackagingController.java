package br.com.rotafood.api.application.controller.v1;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rotafood.api.application.dto.catalog.PackagingDto;
import br.com.rotafood.api.application.service.PackagingService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/merchants/{merchantId}/packagings")
public class PackagingController {

    @Autowired
    private PackagingService packagingService;

    @PreAuthorize("hasAuthority('CATALOG')")
    @GetMapping
    public List<PackagingDto> getAll(
            @PathVariable UUID merchantId) {
        return packagingService.getAllByMerchantId(merchantId);
    }

    @PreAuthorize("hasAuthority('CATALOG')")
    @GetMapping("/{packagingId}")
    public PackagingDto getById(
            @PathVariable UUID merchantId,
            @PathVariable UUID packagingId) {
        return new PackagingDto(packagingService.getByIdAndMerchantId(packagingId, merchantId));
    }

    @PreAuthorize("hasAuthority('CATALOG')")
    @PutMapping
    public PackagingDto updateOrCreate(
            @PathVariable UUID merchantId,
            @RequestBody @Valid PackagingDto packagingDto) {
        return new PackagingDto(packagingService.updateOrCreate(packagingDto, merchantId));
    }
}
