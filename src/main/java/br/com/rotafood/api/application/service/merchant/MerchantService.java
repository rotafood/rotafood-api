package br.com.rotafood.api.application.service.merchant;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.rotafood.api.application.dto.address.AddressDto;
import br.com.rotafood.api.application.dto.merchant.MerchantCreateDto;
import br.com.rotafood.api.application.dto.merchant.MerchantDto;
import br.com.rotafood.api.application.dto.merchant.MerchantOwnerCreationDto;
import br.com.rotafood.api.application.dto.merchant.OwnerCreateDto;
import br.com.rotafood.api.application.service.catalog.CatalogService;
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
            null, 
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

    @Transactional
    public Merchant updateMerchant(MerchantDto merchantDto) {

        Merchant merchant = merchantRepository.findById(merchantDto.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Merchant not found"));

        String existingOnlineName = merchant.getOnlineName();
        if ((existingOnlineName == null || !existingOnlineName.equals(merchantDto.onlineName())) &&
            merchantRepository.existsByOnlineName(merchantDto.onlineName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Online name already in use");
        }
        merchant.setName(merchantDto.name());
        merchant.setCorporateName(merchantDto.corporateName());
        merchant.setOnlineName(merchantDto.onlineName());
        merchant.setDescription(merchantDto.description());
        merchant.setImagePath(merchantDto.imagePath());
        merchant.setDocumentType(merchantDto.documentType());
        merchant.setDocument(merchantDto.document());
        merchant.setMerchantType(merchantDto.merchantType());


        if (merchantDto.address() != null) {
            Address currentAddress = merchant.getAddress();

            if (currentAddress != null) {
                currentAddress.updateFromDto(merchantDto.address());
                addressRepository.save(currentAddress);
            } else {
                Address newAddress = new Address(merchantDto.address());
                addressRepository.save(newAddress);
                merchant.setAddress(newAddress);
            }
        }

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

    public boolean existMerchantByOnlineName(String onlineName) {
        return this.merchantRepository.existsByOnlineName(onlineName);
    }

    public Merchant getMerchantById(UUID id) {
        return this.merchantRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Merchant not found"));
    }
}
