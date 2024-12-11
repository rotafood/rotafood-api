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

import br.com.rotafood.api.application.dto.address.AddressDto;
import br.com.rotafood.api.application.dto.merchant.MerchantCreateDto;
import br.com.rotafood.api.application.dto.merchant.MerchantOwnerCreationDto;
import br.com.rotafood.api.application.dto.merchant.OwnerCreateDto;
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
        validateOwnerEmail(merchantOwnerCreationDto.owner().email());

        Address address = createAddress(merchantOwnerCreationDto.merchant().address());
        Merchant merchant = createMerchantEntity(merchantOwnerCreationDto.merchant(), address);
        MerchantUser merchantUser = createMerchantUser(merchantOwnerCreationDto.owner(), merchant);

        merchantUser.setHasOwner(true);

        merchantUser = this.merchantUserRepository.save(merchantUser);

        catalogService.createDefaultCatalogsForMerchant(merchant);

        return merchantUser;
    }

    private void validateOwnerEmail(String email) {
        if (merchantUserRepository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists!");
        }
    }

    private Address createAddress(AddressDto addressDto) {
        return addressRepository.save(new Address(addressDto));
    }

    private Merchant createMerchantEntity(MerchantCreateDto merchantDto, Address address) {
        Merchant merchant = new Merchant(
            null, 
            merchantDto.name(), 
            merchantDto.corporateName(),
            merchantDto.corporateName(), 
            merchantDto.description(), 
            merchantDto.documentType(),
            merchantDto.document(), 
            merchantDto.merchantType(), 
            Date.from(LocalDateTime.now().atZone(ZoneId.of("America/Sao_Paulo")).toInstant()), 
            null,
            address, 
            null, 
            null
        );
        return merchantRepository.save(merchant);
    }

    private MerchantUser createMerchantUser(OwnerCreateDto ownerDto, Merchant merchant) {
        MerchantUser merchantUser = new MerchantUser();
        merchantUser.setName(ownerDto.name());
        merchantUser.setEmail(ownerDto.email());
        merchantUser.setPassword(ownerDto.password());
        merchantUser.setPhone(ownerDto.phone());
        merchantUser.setMerchantPermissions(Arrays.stream(MerchantPermission.values())
                                                   .map(Enum::name)
                                                   .collect(Collectors.toList()));
        merchantUser.setMerchant(merchant);
        return merchantUserRepository.save(merchantUser);
    }

    public MerchantUser getMerchantUserByEmail(String email) {
        return this.merchantUserRepository.findByEmail(email);
    }
    
    
}
