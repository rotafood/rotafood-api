package br.com.rotafood.api.application.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.rotafood.api.application.dto.merchant.MerchantOwnerCreationDto;
import br.com.rotafood.api.domain.entity.address.Address;
import br.com.rotafood.api.domain.entity.merchant.Merchant;
import br.com.rotafood.api.domain.entity.merchant.MerchantPermission;
import br.com.rotafood.api.domain.entity.merchant.MerchantUser;
import br.com.rotafood.api.domain.repository.AddressRepository;
import br.com.rotafood.api.domain.repository.MerchantRepository;
import br.com.rotafood.api.domain.repository.MerchantUserRepository;
import jakarta.transaction.Transactional;

@Service
public class MerchantService {
    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private MerchantUserRepository merchantUserRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CatalogService catalogService;

    @Transactional
    public MerchantUser createMerchant(MerchantOwnerCreationDto merchantOwnerCreationDto) {
    var addressDto = merchantOwnerCreationDto.merchant().address();
    var ownerDto = merchantOwnerCreationDto.owner();
    var merchantDto = merchantOwnerCreationDto.merchant();
    if (this.merchantUserRepository.existsByEmail(ownerDto.email())) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists!");
    }

    var address = this.addressRepository.save(new Address(addressDto));


    var merchant = this.merchantRepository.save(new Merchant(
        null, 
        merchantDto.name(),
        merchantDto.corporateName(),
        merchantDto.description(),
        merchantDto.document(),
        merchantDto.merchantType(),
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
            ownerDto.name(),
            ownerDto.email(),
            ownerDto.password(),
            ownerDto.phone(),
            ownerDto.document(),
            Arrays.stream(MerchantPermission.values())
            .collect(Collectors.toList()),
            merchant
        )
    );

    this.catalogService.createDefaultCatalogsForMerchant(merchant);

    return merchantUser;

    }
}
