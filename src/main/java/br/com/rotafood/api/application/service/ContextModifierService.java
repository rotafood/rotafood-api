package br.com.rotafood.api.application.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rotafood.api.application.dto.catalog.ContextModifierDto;
import br.com.rotafood.api.domain.entity.catalog.CatalogContext;
import br.com.rotafood.api.domain.entity.catalog.ContextModifier;
import br.com.rotafood.api.domain.entity.catalog.Item;
import br.com.rotafood.api.domain.entity.catalog.Price;
import br.com.rotafood.api.domain.repository.ContextModifierRepository;
import br.com.rotafood.api.domain.repository.ItemRepository;
import jakarta.transaction.Transactional;

@Service
public class ContextModifierService {

    @Autowired
    private ContextModifierRepository contextModifierRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PriceService priceService;

    @Transactional
    public List<ContextModifier> updateOrCreateAll(List<ContextModifierDto> contextModifierDtos) {

    
        return List.of(CatalogContext.values()).stream().map(catalogContext -> {

            ContextModifierDto contextModifierDto = contextModifierDtos.stream()
                    .filter(dto -> dto.catalogContext() == catalogContext)
                    .findFirst()
                    .orElse(null);

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
    public void deleteByRelatedId(UUID relatedId, String relatedType) {
        if ("Item".equalsIgnoreCase(relatedType)) {
            Item item = itemRepository.findById(relatedId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid relatedId: No associated Item found."));
            contextModifierRepository.deleteAll(item.getContextModifiers());
        } 
        else {
            throw new IllegalArgumentException("Invalid relatedType: Must be 'Item' or 'Option'.");
        }
    }

    @Transactional
    public void deleteAll(List<ContextModifier> contextModifiers) {
        contextModifierRepository.deleteAll(contextModifiers);
        System.err.println(contextModifiers.size() + "DELETOOOOOOOOOOOOOOOOOOOOOo");

    }
    
}
