package br.com.rotafood.api.application.service;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rotafood.api.application.dto.catalog.ItemDto;
import br.com.rotafood.api.application.dto.catalog.PriceDto;
import br.com.rotafood.api.application.dto.catalog.ScalePriceDto;
import br.com.rotafood.api.domain.entity.catalog.Category;
import br.com.rotafood.api.domain.entity.catalog.ContextModifier;
import br.com.rotafood.api.domain.entity.catalog.Item;
import br.com.rotafood.api.domain.entity.catalog.ItemOptionGroup;
import br.com.rotafood.api.domain.entity.catalog.Product;
import br.com.rotafood.api.domain.entity.merchant.Merchant;
import br.com.rotafood.api.domain.repository.CategoryRepository;
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
    private CategoryRepository categoryRepository;

    @Autowired ShiftService shiftService;

    @Autowired
    private ContextModifierService contextModifierService;

    @Autowired
    private ItemOptionGroupService itemOptionGroupService;

    @Autowired PriceService priceService;

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
        Category category = categoryRepository.findByIdAndMerchantId(itemDto.categoryId(), merchantId);

        item.setMerchant(merchant);
        item.setStatus(itemDto.status());
        item.setIndex(itemDto.index());
        item.setCategory(category);
        item.setPrice(priceService.updateOrCreate(itemDto.price()));

        List<ContextModifier> contextModifiers = contextModifierService.updateOrCreateAll(
            new PriceDto(
                null,
                itemDto.price().value(),
                itemDto.price().originalValue(),
                itemDto.price().scalePrices().stream()
                    .map(scalePrice -> new ScalePriceDto(null, scalePrice.minQuantity(), scalePrice.value()))
                    .toList()
            ),
            itemDto.contextModifiers()
        );
        item.setContextModifiers(contextModifiers);

        Product product = productService.updateOrCreate(itemDto.product(), merchantId);
        item.setProduct(product);
        product.setItem(item);

        if (itemDto.optionGroups() != null && !itemDto.optionGroups().isEmpty()) {
            List<ItemOptionGroup> itemOptionGroups = itemOptionGroupService.createOrUpdate(
                item.getId(),
                itemDto.optionGroups(),
                merchantId
            );
            item.setItemOptionGroups(itemOptionGroups);
        } else {
            if (item.getItemOptionGroups() != null && !item.getItemOptionGroups().isEmpty()) {
                itemOptionGroupService.createOrUpdate(item.getId(), List.of(), merchantId);
            }
            item.setItemOptionGroups(List.of());
        }

        return itemRepository.save(item);
    }

    


    public void deleteByIdAndMerchantId(UUID productId, UUID merchantId) {
        productService.deleteById(productId, merchantId);
    }

    public List<Item> getAllByMerchantId(UUID merchantId) {
        return this.itemRepository.getAllByMerchantId(merchantId);
    }


  
}
