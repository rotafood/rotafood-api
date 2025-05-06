package br.com.rotafood.api.catalog.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rotafood.api.catalog.application.dto.PriceDto;
import br.com.rotafood.api.catalog.domain.entity.Price;
import br.com.rotafood.api.merchant.domain.repository.PriceRepository;
import jakarta.transaction.Transactional;

@Service
public class PriceService {

    @Autowired
    private PriceRepository priceRepository;


    @Transactional
    public Price updateOrCreate(PriceDto priceDto) {
        Price price = priceDto.id() != null
                ? priceRepository.findById(priceDto.id())
                        .orElse(new Price())
                : new Price();

        price.setValue(priceDto.value());
        price.setOriginalValue(priceDto.originalValue());

        return priceRepository.save(price);
    }
}
