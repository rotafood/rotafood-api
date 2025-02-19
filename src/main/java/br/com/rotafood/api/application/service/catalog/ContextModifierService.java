package br.com.rotafood.api.application.service.catalog;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rotafood.api.application.dto.catalog.ContextModifierDto;
import br.com.rotafood.api.domain.entity.catalog.ContextModifier;
import br.com.rotafood.api.domain.entity.catalog.Price;
import br.com.rotafood.api.domain.repository.ContextModifierRepository;
import jakarta.transaction.Transactional;

@Service
public class ContextModifierService {

    @Autowired
    private ContextModifierRepository contextModifierRepository;

    @Autowired
    private PriceService priceService;

    @Transactional
    public List<ContextModifier> updateOrCreateAll(List<ContextModifierDto> contextModifierDtos) {
        return contextModifierDtos.stream().map(contextModifierDto -> {
            ContextModifier contextModifier = contextModifierDto.id() != null
                    ? contextModifierRepository.findById(contextModifierDto.id())
                        .orElse(new ContextModifier())
                    : new ContextModifier();

            contextModifier.setCatalogContext(contextModifierDto.catalogContext());
            contextModifier.setStatus(contextModifierDto.status());

            if (contextModifierDto.price() != null) {                
                Price price = priceService.updateOrCreate(contextModifierDto.price());
                contextModifier.setPrice(price);
            }

            return contextModifierRepository.save(contextModifier);
        }).collect(Collectors.toList());
    }

    @Transactional
    public ContextModifier updateOrCreate(ContextModifierDto contextModifierDto) {

        ContextModifier contextModifier = contextModifierDto.id() != null
                    ? contextModifierRepository.findById(contextModifierDto.id())
                        .orElse(new ContextModifier())
                    : new ContextModifier();

            contextModifier.setCatalogContext(contextModifierDto.catalogContext());
            contextModifier.setStatus(contextModifierDto.status());

            if (contextModifierDto.price() != null) {                
                Price price = priceService.updateOrCreate(contextModifierDto.price());
                contextModifier.setPrice(price);
            }
        return contextModifierRepository.save(contextModifier);
    }

    public Optional<ContextModifier> findById(UUID id) {
        return this.contextModifierRepository.findById(null);
    }

    

    @Transactional
    public void deleteAll(List<ContextModifier> contextModifiers) {
        contextModifierRepository.deleteAll(contextModifiers);
    }

    @Transactional
    public void delete(ContextModifier contextModifier) {
        contextModifier.setItem(null);
        contextModifier.setOption(null);
        contextModifier.setParentOptionModifier(null);
        contextModifierRepository.delete(contextModifier);
    }
    
}
