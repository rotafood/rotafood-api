package br.com.rotafood.api.application.service.catalog;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rotafood.api.application.dto.SortRequestDto;
import br.com.rotafood.api.application.dto.catalog.CategoryDto;
import br.com.rotafood.api.domain.entity.catalog.Category;
import br.com.rotafood.api.domain.entity.merchant.Merchant;
import br.com.rotafood.api.domain.repository.CategoryRepository;
import br.com.rotafood.api.domain.repository.ItemRepository;
import br.com.rotafood.api.domain.repository.MerchantRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoryService {

    @Autowired private CategoryRepository categoryRepository;
    @Autowired private MerchantRepository merchantRepository;
    @Autowired private ItemRepository itemRepository;


    public Category getByIdAndMerchantId(UUID categoryId, UUID merchantId) {
        return categoryRepository.findByIdAndMerchantId(categoryId, merchantId);
    }

    @Transactional
    public void deleteByIdAndMerchantId(UUID categoryId, UUID merchantId) {
        Category category = categoryRepository.findByIdAndMerchantId(categoryId, merchantId);

        if (category == null) {
            throw new EntityNotFoundException("Categoria não encontrada.");
        }
        categoryRepository.delete(category);
    }


    public List<Category> getAllByMerchantId(UUID merchantId) {
        return categoryRepository.findAllByMerchantIdWithItems(merchantId);
    }



    @Transactional
    public Category updateOrCreate(CategoryDto categoryDto, UUID merchantId) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new EntityNotFoundException("Merchant não encontrado."));

        Category category = categoryDto.id() != null
                ? categoryRepository.findByIdAndMerchantId(categoryDto.id(), merchantId)
                : new Category();

        category.setName(categoryDto.name());
        category.setTemplate(categoryDto.template());
        category.setStatus(categoryDto.status());
        category.setMerchant(merchant);

        List<Category> categories = categoryRepository.findAllByMerchantId(merchantId);

        int newIndex = categoryDto.index() == -1 
                ? categories.stream().map(Category::getIndex).max(Integer::compareTo).orElse(0) + 1
                : categoryDto.index();

        category.setIndex(newIndex + 1);

        Category savedCategory = categoryRepository.save(category);

        return savedCategory;
    }

    @Transactional
    public void sortItemsInCategory(UUID merchantId, UUID categoryId, List<SortRequestDto> sortedItems) {
        sortedItems.forEach(dto -> 
            itemRepository.updateItemIndex(categoryId, dto.id(), dto.index()));
    }

    @Transactional
    public void sortCategories(UUID merchantId, List<SortRequestDto> sortedCategories) {

        sortedCategories.forEach(dto -> 
            {
                categoryRepository.updateCategoryIndex(merchantId, dto.id(), dto.index());
            });
    }
}