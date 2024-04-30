package br.com.rotafood.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.rotafood.api.domain.merchant.repositories.MerchantUserRepository;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    private MerchantUserRepository merchantUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.merchantUserRepository.findByEmail(email);
    }
    
}
