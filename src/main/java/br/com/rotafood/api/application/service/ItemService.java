package br.com.rotafood.api.application.service;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rotafood.api.application.dto.catalog.ItemDto;
import br.com.rotafood.api.domain.entity.catalog.Category;
import br.com.rotafood.api.domain.entity.catalog.ContextModifier;
import br.com.rotafood.api.domain.entity.catalog.Item;
import br.com.rotafood.api.domain.entity.catalog.TemplateType;
import br.com.rotafood.api.domain.entity.catalog.Product;
import br.com.rotafood.api.domain.entity.catalog.Shift;
import br.com.rotafood.api.domain.entity.merchant.Merchant;
import br.com.rotafood.api.domain.repository.CategoryRepository;
import br.com.rotafood.api.domain.repository.ItemRepository;
import br.com.rotafood.api.domain.repository.MerchantRepository;
import br.com.rotafood.api.domain.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ItemService {

    @Autowired
    private ProductService productService;

    @Autowired
    private ItemRepository itemRepository;
    
    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired 
    private ShiftService shiftService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ContextModifierService contextModifierService;


    public Item getByIdAndMerchantId(UUID id, UUID merchantId) {
        Item item = itemRepository.findByIdAndMerchantId(id, merchantId);
        if (item == null) {
            throw new EntityNotFoundException("Item não encontrada.");
        }
        return item;
    }

    @Transactional
    public Item updateOrCreate(ItemDto itemDto, UUID merchantId) {
        Item item = itemDto.id() != null
            ? itemRepository.findByIdAndMerchantId(itemDto.id(), merchantId)
            : new Item();
    
        Merchant merchant = merchantRepository.getReferenceById(merchantId);
    
        if (itemDto.type() == TemplateType.PIZZA) {
            return this.updateOrCreatePizza(itemDto, merchantId);
        }
    
        Category category = categoryRepository.findByIdAndMerchantId(itemDto.categoryId(), merchantId);
        
        item.setMerchant(merchant);
        item.setCategory(category);
        item.setType(itemDto.type());
        item.setStatus(itemDto.status());
        item.setIndex(itemDto.index());
    
        Product product = productService.updateOrCreate(itemDto.product(), merchantId);
        item.setProduct(product);
    
        item.getContextModifiers().clear();
        List<ContextModifier> contextModifiers = contextModifierService.updateOrCreateAll(itemDto.contextModifiers());
        item.getContextModifiers().addAll(contextModifiers);

        item.getShifts().clear();
        List<Shift> shifts = shiftService.updateOrCreateAll(itemDto.shifts());
        item.getShifts().addAll(shifts);

        itemRepository.save(item);
        
        contextModifiers.forEach(cm -> cm.setItem(item));
        shifts.forEach(shift -> shift.setItem(item));
        product.setItem(item);
    
        return item;
    }
    
    @Transactional
    public Item updateOrCreatePizza(ItemDto itemDto, UUID merchantId) {

        Item item = itemDto.id() != null
        ? itemRepository.findByIdAndMerchantId(itemDto.id(), merchantId)
        : new Item();
        
        Merchant merchant = merchantRepository.getReferenceById(merchantId);

        Category category = categoryRepository.findByIdAndMerchantId(itemDto.categoryId(), merchantId);
        
        item.setMerchant(merchant);
        item.setCategory(category);
        item.setType(itemDto.type());
        item.setStatus(itemDto.status());
        item.setIndex(itemDto.index());

        contextModifierService.deleteAll(item.getContextModifiers());
        item.getContextModifiers().clear();
        List<ContextModifier> contextModifiers = contextModifierService.updateOrCreateAll(itemDto.contextModifiers());
        contextModifiers.forEach(cm -> cm.setItem(item));
        item.setContextModifiers(contextModifiers);

        Product product = productService.updateOrCreate(itemDto.product(), merchantId);
        item.setProduct(product);
        product.setItem(item);

        
        return new Item();
    }

    public void deleteByIdAndMerchantId(UUID itemId, UUID merchantId) {
        var item = itemRepository.findByIdAndMerchantId(itemId, merchantId);
        if (item != null) {
            
            productRepository.delete(item.getProduct());
        }
    }

    public List<Item> getAllByMerchantId(UUID merchantId) {
        return this.itemRepository.getAllByMerchantId(merchantId);
    }



  
}
