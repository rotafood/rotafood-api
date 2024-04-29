package br.com.rotafood.api.domain.merchant.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.rotafood.api.domain.address.models.Address;
import br.com.rotafood.api.domain.address.repositories.AddressRepository;
import br.com.rotafood.api.domain.merchant.dtos.CreateMerchantDto;
import br.com.rotafood.api.domain.merchant.models.Merchant;
import br.com.rotafood.api.domain.merchant.models.MerchantPermission;
import br.com.rotafood.api.domain.merchant.models.MerchantUser;
import br.com.rotafood.api.domain.merchant.repositories.MerchantRepository;
import br.com.rotafood.api.domain.merchant.repositories.MerchantUserRepository;
import jakarta.transaction.Transactional;

@Service
public class MerchantService {
    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private MerchantUserRepository merchantUserRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Transactional
    public MerchantUser createMerchant(CreateMerchantDto createMerchantDto) {
    if (this.merchantUserRepository.existsByEmail(createMerchantDto.owner().email())) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists!");
    }

    var address = this.addressRepository.save(new Address(createMerchantDto.address()));


    var merchant = this.merchantRepository.save(new Merchant(
        null, 
        createMerchantDto.name(),
        createMerchantDto.corporateName(),
        createMerchantDto.description(),
        createMerchantDto.document(),
        createMerchantDto.merchantType(),
        Date.from(
        LocalDateTime.now().atZone(
            ZoneId.of("America/Sao_Paulo")
            ).toInstant()
        ),
        address, 
        null
    ));

    var merchantUser = this.merchantUserRepository.save(
        new MerchantUser(
            null,
            createMerchantDto.owner().name(),
            createMerchantDto.owner().email(),
            createMerchantDto.owner().password(),
            createMerchantDto.owner().phone(),
            createMerchantDto.owner().document(),
            Arrays.stream(MerchantPermission.values())
            .collect(Collectors.toList()),
            merchant
        )
    );

    return merchantUser;

    }
}
