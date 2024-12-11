package br.com.rotafood.api.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rotafood.api.application.dto.catalog.PriceDto;
import br.com.rotafood.api.domain.entity.catalog.Price;
import br.com.rotafood.api.domain.entity.catalog.ScalePrice;
import br.com.rotafood.api.domain.repository.PriceRepository;
import br.com.rotafood.api.domain.repository.ScalePriceRepository;
import jakarta.transaction.Transactional;

@Service
public class PriceService {

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private ScalePriceRepository scalePriceRepository;

    @Transactional
    public Price updateOrCreate(PriceDto priceDto) {
        Price price = priceDto.id() != null
                ? priceRepository.findById(priceDto.id())
                        .orElse(new Price())
                : new Price();

        price.setValue(priceDto.value());
        price.setOriginalValue(priceDto.originalValue());

        updateScalePrices(price, priceDto);
        return priceRepository.save(price);
    }

    private void updateScalePrices(Price price, PriceDto priceDto) {
        // Remove os ScalePrices antigos
        if (price.getScalePrices() != null) {
            scalePriceRepository.deleteAll(price.getScalePrices());
            price.getScalePrices().clear();
        }
    
        if (priceDto.scalePrices() != null) {
            List<ScalePrice> scalePrices = priceDto.scalePrices().stream()
                .filter(scalePriceDto -> scalePriceDto.value() != null)
                .map(scalePriceDto -> {
                    ScalePrice scalePrice = new ScalePrice();
                    scalePrice.setMinQuantity(scalePriceDto.minQuantity());
                    scalePrice.setValue(scalePriceDto.value());
                    scalePrice.setPrice(price);
                    return scalePrice;
                })
                .toList();
            price.setScalePrices(scalePrices);
            scalePriceRepository.saveAll(scalePrices);
        }
    }
}
