package br.com.rotafood.api.application.service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rotafood.api.application.dto.catalog.CatalogDto;
import br.com.rotafood.api.domain.entity.catalog.Catalog;
import br.com.rotafood.api.domain.entity.catalog.CatalogCategory;
import br.com.rotafood.api.domain.entity.catalog.CatalogContext;
import br.com.rotafood.api.domain.entity.catalog.Category;
import br.com.rotafood.api.domain.entity.catalog.Status;
import br.com.rotafood.api.domain.entity.merchant.Merchant;
import br.com.rotafood.api.domain.repository.CatalogCategoryRepository;
import br.com.rotafood.api.domain.repository.CatalogRepository;
import br.com.rotafood.api.domain.repository.CategoryRepository;
import br.com.rotafood.api.domain.repository.MerchantRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CatalogService {

    @Autowired private CatalogRepository catalogRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private MerchantRepository merchantRepository;
    @Autowired private CatalogCategoryRepository catalogCategoryRepository;

    public List<CatalogDto> getAllByMerchantId(UUID merchantId) {
        return catalogRepository.findByMerchantId(merchantId)
                .stream()
                .map(CatalogDto::new)
                .toList();
    }

    @Transactional
    public Catalog getByIdAndMerchantId(UUID catalogId, UUID merchantId) {
        return catalogRepository.findByIdAndMerchantId(catalogId, merchantId);
    }

    @Transactional
    public Catalog updateOrCreate(CatalogDto catalogDto, UUID merchantId) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new EntityNotFoundException("Merchant não encontrado."));

        Catalog catalog;

        if (catalogDto.id() != null) {
            catalog = catalogRepository.findByIdAndMerchantId(catalogDto.id(), merchantId);
            if (catalog == null) {
                throw new EntityNotFoundException("Catálogo não encontrado.");
            }
            catalog.setModifiedAt(new Date());
            catalog.setStatus(catalogDto.status());
            catalog.setCatalogContext(catalogDto.catalogContext());
        } else {
            catalog = new Catalog();
            catalog.setModifiedAt(new Date());
            catalog.setStatus(catalogDto.status());
            catalog.setCatalogContext(catalogDto.catalogContext());
            catalog.setMerchant(merchant);
            catalog.setIFoodCatalogId(catalogDto.iFoodCatalofId());
        }

        catalog = catalogRepository.save(catalog);

        // Garantir que todas as categorias do merchant estejam associadas ao catálogo
        associateAllCategoriesWithCatalog(catalog, merchantId);

        return catalog;
    }

    private void associateAllCategoriesWithCatalog(Catalog catalog, UUID merchantId) {
        List<Category> categories = categoryRepository.findByMerchantId(merchantId);

        // Remover associações antigas
        catalogCategoryRepository.deleteByCatalogId(catalog.getId());

        // Criar novas associações
        Set<CatalogCategory> catalogCategories = categories.stream()
                .map(category -> new CatalogCategory(null, catalog, category))
                .collect(Collectors.toSet());

        catalogCategoryRepository.saveAll(catalogCategories);
    }

    @Transactional
    public void createDefaultCatalogsForMerchant(Merchant merchant) {
        List<CatalogDto> defaultCatalogs = List.of(
            new CatalogDto(null, new Date(), Status.AVALIABLE, CatalogContext.TABLE, null),
                new CatalogDto(null, new Date(), Status.AVALIABLE, CatalogContext.DELIVERY, null),
                new CatalogDto(null, new Date(), Status.AVALIABLE, CatalogContext.IFOOD, null)
        );

        defaultCatalogs.forEach(catalogDto -> updateOrCreate(catalogDto, merchant.getId()));
    }
}
