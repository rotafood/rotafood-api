package br.com.rotafood.api.application.controller.v1;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rotafood.api.application.dto.merchant.MerchantUserCreateDto;
import br.com.rotafood.api.application.dto.merchant.MerchantUserDto;
import br.com.rotafood.api.application.dto.merchant.MerchantUserUpdateDto;
import br.com.rotafood.api.application.service.merchant.MerchantUserService;
import br.com.rotafood.api.domain.entity.merchant.MerchantUserRole;
import br.com.rotafood.api.infra.security.MerchantRoleAllowed;
import jakarta.validation.Valid;

@MerchantRoleAllowed({MerchantUserRole.OWNER, MerchantUserRole.ADMIN})
@RestController
@RequestMapping(ApiVersion.VERSION + "/merchants/{merchantId}/users")
public class MerchantUserController {

    @Autowired
    private MerchantUserService merchantUserService;

    @GetMapping
    public List<MerchantUserDto> list(@PathVariable UUID merchantId) {
        return merchantUserService.getUsersByMerchantId(merchantId).stream()
            .filter(user -> user.getRole() != MerchantUserRole.OWNER)
            .map(MerchantUserDto::new)
            .toList();
    }
    


    @PostMapping
    public MerchantUserDto create(
        @PathVariable UUID merchantId,
        @Valid @RequestBody MerchantUserCreateDto dto
    ) {
        return new MerchantUserDto(merchantUserService.createMerchantUser(merchantId, dto));
    }

    @PutMapping("/{merchantUserId}")
    public MerchantUserDto update(
        @PathVariable UUID merchantId,
        @PathVariable UUID merchantUserId,
        @Valid @RequestBody MerchantUserUpdateDto dto
    ) {
        return new MerchantUserDto(merchantUserService.updateMerchantUser(merchantId, merchantUserId, dto));
    }

    @DeleteMapping("/{merchantUserId}")
    public MerchantUserDto delete(
        @PathVariable UUID merchantId,
        @PathVariable UUID merchantUserId
    ) {
        return new MerchantUserDto(merchantUserService.deleteMerchantUser(merchantId, merchantUserId));
    }
}
