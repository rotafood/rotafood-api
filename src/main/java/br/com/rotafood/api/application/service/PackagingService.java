package br.com.rotafood.api.application.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rotafood.api.application.dto.catalog.PackagingDto;
import br.com.rotafood.api.domain.entity.catalog.Packaging;
import br.com.rotafood.api.domain.entity.merchant.Merchant;
import br.com.rotafood.api.domain.repository.PackagingRepository;
import br.com.rotafood.api.domain.repository.MerchantRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PackagingService {

    private final PackagingRepository packagingRepository;
    private final MerchantRepository merchantRepository;

    public PackagingService(PackagingRepository packagingRepository, MerchantRepository merchantRepository) {
        this.packagingRepository = packagingRepository;
        this.merchantRepository = merchantRepository;
    }

    public List<PackagingDto> getAllByMerchantId(UUID merchantId) {
        return packagingRepository.findByMerchantId(merchantId)
                .stream()
                .map(PackagingDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Packaging getByIdAndMerchantId(UUID packagingId, UUID merchantId) {
        return packagingRepository.findByIdAndMerchantId(packagingId, merchantId)
                .orElseThrow(() -> new EntityNotFoundException("Packaging not found."));
    }

    @Transactional
    public Packaging updateOrCreate(PackagingDto packagingDto, UUID merchantId) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new EntityNotFoundException("Merchant not found."));

        Packaging packaging;

        if (packagingDto.id() != null) {
            packaging = packagingRepository.findByIdAndMerchantId(packagingDto.id(), merchantId)
                    .orElseThrow(() -> new EntityNotFoundException("Packaging not found."));
            packaging.setName(packagingDto.name());
            packaging.setLenghtCm(packagingDto.lenghtCm());
            packaging.setWidthCm(packagingDto.widthCm());
            packaging.setThicknessCm(packagingDto.thicknessCm());
        } else {
            packaging = new Packaging(
                    null,
                    packagingDto.name(),
                    packagingDto.lenghtCm(),
                    packagingDto.widthCm(),
                    packagingDto.thicknessCm(),
                    merchant
            );
        }

        return packagingRepository.save(packaging);
    }
}