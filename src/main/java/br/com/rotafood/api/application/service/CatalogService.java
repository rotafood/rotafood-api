package br.com.rotafood.api.application.service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rotafood.api.application.dto.catalog.CatalogDto;
import br.com.rotafood.api.domain.entity.catalog.Catalog;
import br.com.rotafood.api.domain.entity.catalog.CatalogContext;
import br.com.rotafood.api.domain.entity.catalog.Category;
import br.com.rotafood.api.domain.entity.catalog.Status;
import br.com.rotafood.api.domain.entity.merchant.Merchant;
import br.com.rotafood.api.domain.repository.CatalogRepository;
import br.com.rotafood.api.domain.repository.CategoryRepository;
import br.com.rotafood.api.domain.repository.MerchantRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CatalogService {

    private final CatalogRepository catalogRepository;
    private final CategoryRepository categoryRepository;
    private final MerchantRepository merchantRepository;

    public CatalogService(
            CatalogRepository catalogRepository,
            CategoryRepository categoryRepository,
            MerchantRepository merchantRepository) {
        this.catalogRepository = catalogRepository;
        this.categoryRepository = categoryRepository;
        this.merchantRepository = merchantRepository;
    }

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

        Set<Category> categories = categoryRepository.findByMerchantId(merchantId)
                .stream()
                .collect(Collectors.toSet());

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
            catalog = new Catalog(
                    null,
                    new Date(),
                    catalogDto.status(),
                    catalogDto.catalogContext(),
                    merchant,
                    categories,
                    catalogDto.iFoodCatalofId()
            );
        }

        return catalogRepository.save(catalog);
    }

    @Transactional
    public void createDefaultCatalogsForMerchant(Merchant merchant) {
        updateOrCreate(new CatalogDto(null, new Date(), Status.AVALIABLE, CatalogContext.DELIVERY, null), merchant.getId());
        updateOrCreate(new CatalogDto(null, new Date(), Status.AVALIABLE, CatalogContext.TABLE, null), merchant.getId());
        updateOrCreate(new CatalogDto(null, new Date(), Status.AVALIABLE, CatalogContext.IFOOD, null), merchant.getId());
    }
}
