package br.com.rotafood.api.application.service.merchant;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rotafood.api.application.dto.merchant.LogisticSettingDto;
import br.com.rotafood.api.domain.entity.logistic.LogisticSetting;
import br.com.rotafood.api.domain.entity.merchant.Merchant;
import br.com.rotafood.api.domain.repository.LogisticSettingRepository;
import br.com.rotafood.api.domain.repository.MerchantRepository;
import jakarta.persistence.EntityNotFoundException;


@Service
public class LogisticSettingService {

    @Autowired
    private LogisticSettingRepository logisticSettingRepository;

    @Autowired
    private MerchantRepository merchantRepository;


    public LogisticSetting createOrUpdateSettings(LogisticSettingDto settingDto, UUID merchantId) {

        Merchant merchant = merchantRepository.findById(merchantId).orElseThrow(() -> new EntityNotFoundException("Merchant não encontrado"));

        LogisticSetting setting = settingDto.id() != null
            ? logisticSettingRepository.findByIdAndMerchantId(settingDto.id(), merchantId).orElseThrow(() -> {
                throw new EntityNotFoundException("Logistic não encontrado");
            })
            : new LogisticSetting();

        setting.setKmRadius(settingDto.kmRadius());
        setting.setMinTax(settingDto.minTax());
        setting.setTaxPerKm(settingDto.taxPerKm());
        setting.setMerchant(merchant);

        return logisticSettingRepository.save(setting);
    }

    public LogisticSetting getByMerchantId(UUID merchantId) {
        return this.logisticSettingRepository.findByMerchantId(merchantId).orElseThrow(() -> new EntityNotFoundException("Entidade não ecnotrada"));
    }


    
}
