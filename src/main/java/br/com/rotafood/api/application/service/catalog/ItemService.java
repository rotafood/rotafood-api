package br.com.rotafood.api.application.service.catalog;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rotafood.api.application.dto.catalog.CategoryDto;
import br.com.rotafood.api.application.dto.catalog.ItemDto;
import br.com.rotafood.api.domain.entity.catalog.Category;
import br.com.rotafood.api.domain.entity.catalog.ContextModifier;
import br.com.rotafood.api.domain.entity.catalog.Item;
import br.com.rotafood.api.domain.entity.catalog.TemplateType;
import br.com.rotafood.api.domain.entity.catalog.Product;
import br.com.rotafood.api.domain.entity.catalog.Shift;
import br.com.rotafood.api.domain.entity.catalog.Status;
import br.com.rotafood.api.domain.entity.merchant.Merchant;
import br.com.rotafood.api.domain.repository.ItemRepository;
import br.com.rotafood.api.domain.repository.MerchantRepository;
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
    private CategoryService categoryService;

    @Autowired 
    private ShiftService shiftService;

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
    
        Category category = categoryService.getByIdAndMerchantId(itemDto.categoryId(), merchantId);
        
        item.setMerchant(merchant);
        item.setCategory(category);
        item.setType(itemDto.type());
        item.setStatus(itemDto.status());
        item.setIndex(itemDto.index());
        itemRepository.save(item);

    
        Product product = productService.updateOrCreate(itemDto.product(), merchantId);
        item.setProduct(product);
    
        List<ContextModifier> contextModifiers = contextModifierService.updateOrCreateAll(itemDto.contextModifiers());
        item.getContextModifiers().clear();
        contextModifiers.forEach(item::addContextModifier);
    
        List<Shift> shifts = shiftService.updateOrCreateAll(itemDto.shifts());
        item.getShifts().clear();
        shifts.forEach(item::addShift);
        
        product.setItem(item);
    
        return itemRepository.save(item);
    }
    
    @Transactional
    public Item updateOrCreatePizza(ItemDto itemDto, UUID merchantId) {

        Item item = itemDto.id() != null
        ? itemRepository.findByIdAndMerchantId(itemDto.id(), merchantId)
        : new Item();
        
        Merchant merchant = merchantRepository.getReferenceById(merchantId);

        Category category = itemDto.categoryId() != null ? 
            categoryService.getByIdAndMerchantId(itemDto.categoryId(), merchantId) : 
            this.categoryService.updateOrCreate(
                new CategoryDto(null, null, itemDto.product().name(), TemplateType.PIZZA, Status.AVALIABLE, merchantId
                ), merchantId);
        
        item.setMerchant(merchant);
        item.setCategory(category);
        item.setType(itemDto.type());
        item.setStatus(itemDto.status());
        item.setIndex(itemDto.index());
        itemRepository.save(item);

    
        Product product = productService.updateOrCreate(itemDto.product(), merchantId);
        item.setProduct(product);
    
        List<ContextModifier> contextModifiers = contextModifierService.updateOrCreateAll(itemDto.contextModifiers());
        item.getContextModifiers().clear();
        contextModifiers.forEach(item::addContextModifier);
    
        List<Shift> shifts = shiftService.updateOrCreateAll(itemDto.shifts());
        item.getShifts().clear();
        shifts.forEach(item::addShift);

        product.setItem(item);
    
        return itemRepository.save(item);
    }

    @Transactional
    public void deleteByIdAndMerchantId(UUID itemId, UUID merchantId) {
        var item = itemRepository.findByIdAndMerchantId(itemId, merchantId);
        itemRepository.delete(item);

    }



    public List<Item> getAllByMerchantId(UUID merchantId) {
        return this.itemRepository.getAllByMerchantId(merchantId);
    }



  
}
