package br.com.rotafood.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rotafood.api.repository.MerchantUserRepository;

@Service
public class MerchantUserService {
    @Autowired
    private MerchantUserRepository merchantUserRepository;

    public boolean emailExists(String email) {
        return this.merchantUserRepository.existsByEmail(email);
    }
}
