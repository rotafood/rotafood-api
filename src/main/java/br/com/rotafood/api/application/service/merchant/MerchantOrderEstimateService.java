package br.com.rotafood.api.application.service.merchant;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rotafood.api.application.dto.merchant.MerchantOrderEstimateDto;
import br.com.rotafood.api.domain.entity.merchant.Merchant;
import br.com.rotafood.api.domain.entity.merchant.MerchantOrderEstimate;
import br.com.rotafood.api.domain.repository.MerchantOrderEstimateRepository;
import br.com.rotafood.api.domain.repository.MerchantRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class MerchantOrderEstimateService {

    @Autowired
    private MerchantOrderEstimateRepository merchantOrderEstimateRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    public MerchantOrderEstimate createOrUpdateEstimate(MerchantOrderEstimateDto estimateDto, UUID merchantId) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new EntityNotFoundException("Merchant não encontrado"));

        MerchantOrderEstimate estimate = estimateDto.id() != null
                ? merchantOrderEstimateRepository.findByIdAndMerchantId(estimateDto.id(), merchantId)
                        .orElseThrow(() -> new EntityNotFoundException("Estimate não encontrado"))
                : new MerchantOrderEstimate();

        estimate.setPickupMinMinutes(estimateDto.pickupMinMinutes());
        estimate.setPickupMaxMinutes(estimateDto.pickupMaxMinutes());

        estimate.setDeliveryMinMinutes(estimateDto.deliveryMinMinutes());
        estimate.setDeliveryMaxMinutes(estimateDto.deliveryMaxMinutes());


        if (estimate.getMerchant() == null) {

            estimate.setMerchant(merchant);
        }

        return merchantOrderEstimateRepository.save(estimate);
    }

    public MerchantOrderEstimate getByMerchantId(UUID merchantId) {
        return merchantOrderEstimateRepository.findByMerchantId(merchantId)
                .orElseThrow(() -> new EntityNotFoundException("Entidade não encontrada"));
    }
}