package br.com.rotafood.api.application.service.catalog;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rotafood.api.application.dto.catalog.CategoryDto;
import br.com.rotafood.api.application.dto.catalog.ItemDto;
import br.com.rotafood.api.domain.entity.catalog.Category;
import br.com.rotafood.api.domain.entity.catalog.Item;
import br.com.rotafood.api.domain.entity.catalog.TemplateType;
import br.com.rotafood.api.domain.entity.catalog.Product;
import br.com.rotafood.api.domain.entity.catalog.AvailabilityStatus;
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

        Product product = productService.updateOrCreate(itemDto.product(), merchantId);
        item.setProduct(product);

        itemRepository.save(item);

    
        itemDto.contextModifiers().forEach(cm -> {
            this.contextModifierService.updateOrCreate(cm, item, null, null);
        });
    
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
                new CategoryDto(null, 0, itemDto.product().name(), TemplateType.PIZZA, AvailabilityStatus.AVAILIABLE, null
                ), merchantId);
        
        item.setMerchant(merchant);
        item.setCategory(category);
        item.setType(itemDto.type());
        item.setStatus(itemDto.status());
        

        List<Item> items = itemRepository.findAllByMerchantId(merchantId);

        int newIndex = itemDto.index() == -1 
                ? items.stream().map(Item::getIndex).max(Integer::compareTo).orElse(0) + 1
                : itemDto.index();

        item.setIndex(newIndex + 1);

        Product product = productService.updateOrCreate(itemDto.product(), merchantId);

        item.setProduct(product);

        itemRepository.save(item);

        itemDto.contextModifiers().forEach(cm -> {
            this.contextModifierService.updateOrCreate(cm, item, null, null);
        });
    
        item.getShifts().removeIf(shift -> 
                itemDto.shifts() != null && 
                itemDto.shifts().stream().noneMatch(dto -> dto.id().equals(shift.getId()))
            );

        itemDto.shifts().forEach(cm -> {
            this.shiftService.updateOrCreate(cm, item, null);
        });
    
        return itemRepository.save(item);
    }

    @Transactional
    public void deleteByIdAndMerchantId(UUID itemId, UUID merchantId) {
        var item = itemRepository.findByIdAndMerchantId(itemId, merchantId);
        itemRepository.delete(item);

    }



    public List<Item> getAllByMerchantId(UUID merchantId) {
        return this.itemRepository.findAllByMerchantId(merchantId);
    }

}
