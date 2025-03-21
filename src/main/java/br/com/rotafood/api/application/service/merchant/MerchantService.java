package br.com.rotafood.api.application.service.merchant;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.rotafood.api.application.dto.address.AddressDto;
import br.com.rotafood.api.application.dto.catalog.ShiftDto;
import br.com.rotafood.api.application.dto.merchant.FullMerchantDto;
import br.com.rotafood.api.application.dto.merchant.MerchantCreateDto;
import br.com.rotafood.api.application.dto.merchant.MerchantOwnerCreationDto;
import br.com.rotafood.api.application.dto.merchant.OwnerCreateDto;
import br.com.rotafood.api.application.service.catalog.CatalogService;
import br.com.rotafood.api.domain.entity.address.Address;
import br.com.rotafood.api.domain.entity.catalog.Shift;
import br.com.rotafood.api.domain.entity.merchant.Merchant;
import br.com.rotafood.api.domain.entity.merchant.MerchantUser;
import br.com.rotafood.api.domain.entity.merchant.MerchantUserRole;
import br.com.rotafood.api.domain.entity.order.OrderSalesChannel;
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

        MerchantUser merchantUser = createOwer(merchantOwnerCreationDto.owner(), merchant);

        merchantUser = this.merchantUserRepository.save(merchantUser);

        catalogService.createDefaultCatalogsForMerchant(merchant);

        return merchantUser;
    }

    @Transactional
    public void updateMerchantOpened(UUID merchantId, List<OrderSalesChannel> sources) {
        Merchant merchant = merchantRepository.findById(merchantId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Merchant not found"));

        if (sources.contains(OrderSalesChannel.ROTAFOOD)) {
            merchant.setLastOpenedUtc(Instant.now());
        }

        merchantRepository.save(merchant);
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
            merchantDto.phone(),
            merchantDto.merchantType(), 
            Date.from(LocalDateTime.now().atZone(ZoneId.of("America/Sao_Paulo")).toInstant()), 
            null,
            Instant.now(),
            address, 
            null, 
            null
        );
        return merchantRepository.save(merchant);
    }

    @Transactional
    public Merchant updateMerchant(FullMerchantDto merchantDto) {

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
        merchant.setPhone(merchantDto.phone());
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

        if (merchantDto.openingHours() != null) {
            this.updateOpeningHours(merchant, merchantDto.openingHours());
        }

        return merchantRepository.save(merchant);
    }

    private void updateOpeningHours(Merchant merchant, List<ShiftDto> openingHoursDtos) {
    
        List<UUID> incomingIds = openingHoursDtos.stream()
            .map(ShiftDto::id)
            .filter(Objects::nonNull)
            .toList();
    
        merchant.getOpeningHours().removeIf(shift -> !incomingIds.contains(shift.getId()));
    
        openingHoursDtos.forEach(dto -> {
                Shift shift = merchant.getOpeningHours().stream()
                    .filter(existing -> existing.getId() != null && existing.getId().equals(dto.id()))
                    .findFirst()
                    .orElseGet(() -> {
                        Shift newShift = new Shift();
                        merchant.addShift(newShift);
                        return newShift;
                    });
    
                shift.setStartTime(LocalTime.parse(dto.startTime()));
                shift.setEndTime(LocalTime.parse(dto.endTime()));
                shift.setMonday(dto.monday());
                shift.setTuesday(dto.tuesday());
                shift.setWednesday(dto.wednesday());
                shift.setThursday(dto.thursday());
                shift.setFriday(dto.friday());
                shift.setSaturday(dto.saturday());
                shift.setSunday(dto.sunday());

                merchant.addShift(shift);
                });
    }


    private MerchantUser createOwer(OwnerCreateDto ownerDto, Merchant merchant) {
        MerchantUser merchantUser = new MerchantUser();
        merchantUser.setName(ownerDto.name());
        merchantUser.setEmail(ownerDto.email());
        merchantUser.setPassword(ownerDto.password());
        merchantUser.setPhone(ownerDto.phone());
        merchantUser.setHasOwner(true);
        merchantUser.setRole(MerchantUserRole.ADMIN);
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
