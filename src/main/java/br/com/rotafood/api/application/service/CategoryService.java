package br.com.rotafood.api.application.service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rotafood.api.application.dto.catalog.CategoryDto;
import br.com.rotafood.api.domain.entity.catalog.Catalog;
import br.com.rotafood.api.domain.entity.catalog.Category;
import br.com.rotafood.api.domain.entity.merchant.Merchant;
import br.com.rotafood.api.domain.repository.CatalogRepository;
import br.com.rotafood.api.domain.repository.CategoryRepository;
import br.com.rotafood.api.domain.repository.MerchantRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoryService {

    private final CatalogRepository catalogRepository;
    private final MerchantRepository merchantRepository;
    private final CategoryRepository categoryRepository;

    public CategoryService(
            CatalogRepository catalogRepository,
            MerchantRepository merchantRepository,
            CategoryRepository categoryRepository) {
        this.catalogRepository = catalogRepository;
        this.merchantRepository = merchantRepository;
        this.categoryRepository = categoryRepository;
    }

    public Category getByIdAndMerchantId(UUID categoryId, UUID merchantId) {
        return categoryRepository.findByIdAndMerchantId(categoryId, merchantId);    
    }

    public List<Category> getAllByMerchantId(UUID merchantId) {
        List<Category> categories = categoryRepository.findByMerchantId(merchantId);
        if (categories.isEmpty()) {
            throw new EntityNotFoundException("Categorias não encontradas.");
        }
        return categories;
    }

    @Transactional
    public Category updateOrCreate(CategoryDto categoryDto, UUID merchantId) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new EntityNotFoundException("Merchant não encontrado."));

        Category category = categoryDto.id() != null
                ? categoryRepository.findByIdAndMerchantId(categoryDto.id(), merchantId)
                : new Category();


        category.setName(categoryDto.name());

        if (categoryDto.index() != null) {
            category.setIndex(categoryDto.index());
    
            List<Category> categories = categoryRepository.findByMerchantId(merchantId);
    
            categories.stream()
                .filter(c -> !c.getId().equals(category.getId()))
                .sorted(Comparator.comparingInt(Category::getIndex))
                .forEachOrdered(c -> {
                    if (c.getIndex() >= categoryDto.index()) {
                        c.setIndex(c.getIndex() + 1);
                    }
                });
    
            categoryRepository.saveAll(categories); 
        } else {
            Integer lastIndex = categoryRepository.findByMerchantId(merchantId).stream()
                    .map(Category::getIndex)
                    .max(Integer::compareTo)
                    .orElse(0);
            category.setIndex(lastIndex + 1);
        }
    
        category.setStatus(categoryDto.status());
        category.setMerchant(merchant);
        category.setIFoodCategoryId(categoryDto.iFoodCategoryId());

        Set<Catalog> catalogs = catalogRepository.findByMerchantId(merchantId)
                    .stream()
                    .peek(catalog -> {
                        catalog.getCategories().add(category);
                    })
                    .collect(Collectors.toSet());

        category.setCatalogs(catalogs); 

        return categoryRepository.save(category);
    }

    @Transactional
    public void updateIndexes(List<CategoryDto> categoryDtos, UUID merchantId) {
        List<Category> categoriesToUpdate = categoryDtos.stream()
            .map(dto -> {
                Category category = categoryRepository.findByIdAndMerchantId(dto.id(), merchantId);
                category.setIndex(dto.index());
                return category;
            })
            .toList();

        categoryRepository.saveAll(categoriesToUpdate);
    }


}
