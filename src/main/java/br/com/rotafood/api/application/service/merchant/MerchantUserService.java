package br.com.rotafood.api.application.service.merchant;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.rotafood.api.application.dto.merchant.MerchantUserCreateDto;
import br.com.rotafood.api.application.dto.merchant.MerchantUserUpdateDto;
import br.com.rotafood.api.domain.entity.merchant.Merchant;
import br.com.rotafood.api.domain.entity.merchant.MerchantUser;
import br.com.rotafood.api.domain.entity.merchant.MerchantUserRole;
import br.com.rotafood.api.domain.repository.MerchantRepository;
import br.com.rotafood.api.domain.repository.MerchantUserRepository;
import jakarta.transaction.Transactional;

@Service
public class MerchantUserService {

    @Autowired
    private MerchantUserRepository merchantUserRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    public boolean emailExists(String email) {
        return this.merchantUserRepository.existsByEmail(email);
    }

    @Transactional
    public MerchantUser createMerchantUser(UUID merchantId, MerchantUserCreateDto dto) {
        if (merchantUserRepository.existsByEmail(dto.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já está em uso");
        }

        Merchant merchant = merchantRepository.findById(merchantId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Merchant não encontrado"));

        MerchantUser user = new MerchantUser();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPhone(dto.phone());
        user.setPassword(dto.password());

        if (dto.role() != MerchantUserRole.OWNER) {
            user.setRole(dto.role()); 
        }
        user.setMerchant(merchant);

        return merchantUserRepository.save(user);
    }

    @Transactional
    public MerchantUser updateMerchantUser(UUID merchantId, UUID userId, MerchantUserUpdateDto dto) {
        MerchantUser user = merchantUserRepository.findById(userId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        if (!user.getMerchant().getId().equals(merchantId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuário não pertence a este merchant");
        }

        user.setName(dto.name());
        user.setPhone(dto.phone());

        if (dto.role() != MerchantUserRole.OWNER) {
            user.setRole(dto.role()); 
        }

        return merchantUserRepository.save(user);
    }

    public List<MerchantUser> getUsersByMerchantId(UUID merchantId) {
        return merchantUserRepository.findByMerchantId(merchantId);
    }

    @Transactional
    public MerchantUser deleteMerchantUser(UUID merchantId, UUID userId) {
        MerchantUser user = merchantUserRepository.findById(userId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    
        if (user.getRole() == MerchantUserRole.OWNER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuário com papel OWNER não pode ser deletado");
        }
    
        merchantUserRepository.delete(user);
        return user;
    }
}
