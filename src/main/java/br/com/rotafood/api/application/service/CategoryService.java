package br.com.rotafood.api.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rotafood.api.application.dto.catalog.CategoryDto;
import br.com.rotafood.api.domain.entity.catalog.Catalog;
import br.com.rotafood.api.domain.entity.catalog.Category;
import br.com.rotafood.api.domain.entity.merchant.Merchant;
import br.com.rotafood.api.domain.repository.CatalogRepository;
import br.com.rotafood.api.domain.repository.CategoryRepository;
import br.com.rotafood.api.domain.repository.MerchantRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class CategoryService {

    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Category getByIdAndCatalogIdAndMerchantId(UUID categoryId, UUID merchantId, UUID catalogId) {
        Category category = categoryRepository.findByIdAndCatalogIdAndMerchantId(categoryId, catalogId, merchantId);
        if (category == null) {
            throw new EntityNotFoundException("Categoria não encontrada.");
        }
        return category;
    }

    public List<Category> getAllByCatalogIdAndMerchantId(UUID catalogId, UUID merchantId) {
        List<Category> categories = categoryRepository.findByCatalogIdAndMerchantId(catalogId, merchantId);
        if (categories.isEmpty()) {
            throw new EntityNotFoundException("Categoria não encontrada.");
        }
        return categories;
    }

    @Transactional
    public Category create(CategoryDto categoryDto, UUID merchantId, UUID catalogId) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new EntityNotFoundException("Merchant não encontrado."));

        Catalog catalog = catalogRepository.findById(catalogId)
                .orElseThrow(() -> new EntityNotFoundException("Catalog não encontrado."));

        Category category = new Category(
            null, 
            categoryDto.name(), 
            categoryDto.template(), 
            categoryDto.index(), 
            categoryDto.status(), 
            null, 
            null, 
            merchant, 
            catalog, 
            categoryDto.iFoodCategoryId()
        );

        category.setMerchant(merchant);
        category.setCatalog(catalog);
        return categoryRepository.save(category);
    }

    @Transactional
    public Category update(CategoryDto categoryDto, UUID categoryId, UUID catalogId, UUID merchantId) {
        Category category = categoryRepository.findByIdAndCatalogIdAndMerchantId(categoryId, catalogId, merchantId);

        if (category == null) {
            throw new EntityNotFoundException("Categoria não encontrada.");
        }

        category.setName(categoryDto.name());
        category.setTemplate(categoryDto.template());
        category.setIndex(categoryDto.index());
        category.setStatus(categoryDto.status());

        return categoryRepository.save(category);
    }
}
