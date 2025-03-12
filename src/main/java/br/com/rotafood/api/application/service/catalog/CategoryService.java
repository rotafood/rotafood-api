package br.com.rotafood.api.application.service.catalog;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rotafood.api.application.dto.SortRequestDto;
import br.com.rotafood.api.application.dto.catalog.CategoryDto;
import br.com.rotafood.api.domain.entity.catalog.Catalog;
import br.com.rotafood.api.domain.entity.catalog.CatalogCategory;
import br.com.rotafood.api.domain.entity.catalog.Category;
import br.com.rotafood.api.domain.entity.merchant.Merchant;
import br.com.rotafood.api.domain.repository.CatalogCategoryRepository;
import br.com.rotafood.api.domain.repository.CatalogRepository;
import br.com.rotafood.api.domain.repository.CategoryRepository;
import br.com.rotafood.api.domain.repository.ItemRepository;
import br.com.rotafood.api.domain.repository.MerchantRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;

@Service
public class CategoryService {

    @Autowired private CatalogRepository catalogRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private MerchantRepository merchantRepository;
    @Autowired private ItemRepository itemRepository;
    @Autowired private CatalogCategoryRepository catalogCategoryRepository;

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

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
        return categoryRepository.findByMerchantId(merchantId);
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
        category.setIFoodCategoryId(categoryDto.iFoodCategoryId());

        if (categoryDto.index() == 1) {
            adjustIndexesForUpdate(categoryDto.index(), category, merchantId);
        } else {
            Integer lastIndex = categoryRepository.findByMerchantId(merchantId).stream()
                    .map(Category::getIndex)
                    .max(Integer::compareTo)
                    .orElse(0);
            category.setIndex(lastIndex + 1);
        }

        Category savedCategory = categoryRepository.save(category);

        associateCategoryWithAllCatalogs(savedCategory, merchantId);

        return savedCategory;
    }



    private void adjustIndexesForUpdate(Integer newIndex, Category category, UUID merchantId) {
        List<Category> categories = categoryRepository.findByMerchantId(merchantId);

        categories.stream()
                .filter(c -> !c.getId().equals(category.getId()))
                .sorted(Comparator.comparingInt(Category::getIndex))
                .forEachOrdered(c -> {
                    if (c.getIndex() >= newIndex) {
                        c.setIndex(c.getIndex() + 1);
                    }
                });

        categoryRepository.saveAll(categories);
        category.setIndex(newIndex);
    }

    private void associateCategoryWithAllCatalogs(Category category, UUID merchantId) {
        List<Catalog> catalogs = catalogRepository.findByMerchantId(merchantId);

        catalogCategoryRepository.deleteByCategoryId(category.getId());

        Set<CatalogCategory> catalogCategories = catalogs.stream()
                .map(catalog -> new CatalogCategory(null, catalog, category))
                .collect(Collectors.toSet());

        catalogCategoryRepository.saveAll(catalogCategories);
    }

    @Transactional
    public void sortItemsInCategory(UUID merchantId, UUID categoryId, List<SortRequestDto> sortedItems) {
        sortedItems.forEach(dto -> 
            itemRepository.updateItemIndex(categoryId, dto.id(), dto.index()));
    }

    @Transactional
    public void sortCategories(UUID merchantId, List<SortRequestDto> sortedCategories) {
        sortedCategories.forEach(dto -> 
            categoryRepository.updateCategoryIndex(merchantId, dto.id(), dto.index()));
    }
}