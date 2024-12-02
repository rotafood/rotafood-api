package br.com.rotafood.api.application.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rotafood.api.application.dto.catalog.CatalogDto;
import br.com.rotafood.api.domain.entity.catalog.Catalog;
import br.com.rotafood.api.domain.entity.catalog.CatalogContext;
import br.com.rotafood.api.domain.entity.catalog.Status;
import br.com.rotafood.api.domain.entity.merchant.Merchant;
import br.com.rotafood.api.domain.repository.CatalogRepository;
import br.com.rotafood.api.domain.repository.MerchantRepository;
import jakarta.transaction.Transactional;


@Service
public class CatalogService {

    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    
    
    public List<CatalogDto> getAllByMerchantId(UUID merchantId) {
        return catalogRepository.findByMerchantId(merchantId)
            .stream()
            .map(CatalogDto::new)
            .toList();
    }
    
    @Transactional
    public CatalogDto getByIdAndMerchantId(UUID catalogId, UUID MerchantId) {
        return new CatalogDto(catalogRepository.findByIdAndMerchantId(catalogId, MerchantId));
    }

    @Transactional
    public CatalogDto createCatalog(CatalogDto catalogDto, UUID merchantId) {
        Merchant merchant = merchantRepository.getReferenceById(merchantId);
        Catalog catalog = new Catalog(
            null,
            catalogDto.modifiedAt(),
            catalogDto.status(),
            catalogDto.catalogContext(),
            merchant,
            null
        );
        return new CatalogDto(catalogRepository.save(catalog));
    }

    public CatalogDto updateCatalog(CatalogDto catalogDto, UUID merchantId) {
        Catalog catalog = this.catalogRepository.findByIdAndMerchantId(catalogDto.id(), merchantId);
        catalog.setModifiedAt(new Date());
        catalog.setStatus(catalogDto.status());
        catalog.setCatalogContext(catalogDto.catalogContext());
        return new CatalogDto(catalog);
    }

    @Transactional
    public void createDefaultCatalogsForMerchant(Merchant merchant) {
        createCatalog(new CatalogDto(null, new Date(), Status.AVALIABLE, CatalogContext.DELIVERY), merchant.getId());
        createCatalog(new CatalogDto(null, new Date(), Status.AVALIABLE, CatalogContext.TABLE), merchant.getId());
        createCatalog(new CatalogDto(null, new Date(), Status.AVALIABLE, CatalogContext.IFOOD), merchant.getId());
    }
}
