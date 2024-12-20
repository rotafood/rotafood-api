package br.com.rotafood.api.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rotafood.api.application.dto.catalog.ProductPackagingDto;
import br.com.rotafood.api.domain.entity.catalog.ProductPackaging;
import br.com.rotafood.api.domain.entity.catalog.Packaging;
import br.com.rotafood.api.domain.repository.ProductPackagingRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductPackagingService {


    @Autowired 
    private PackagingService packagingService;
    @Autowired 
    private ProductPackagingRepository productPackagingRepository;


    public List<ProductPackagingDto> getAllByProductId(UUID productId) {
        return productPackagingRepository.findByProductId(productId)
                .stream()
                .map(ProductPackagingDto::new)
                .toList();
    }

    @Transactional
    public List<ProductPackaging> createOrUpdateAll(List<ProductPackagingDto> productPackagingDtos, UUID productId, UUID merchantId) {
        // Obter as ProductPackaging existentes no banco relacionadas ao produto
        List<ProductPackaging> existingProductPackagings = productPackagingRepository.findByProductId(productId);

        // Obter os IDs das ProductPackaging recebidas nos DTOs
        List<UUID> incomingIds = productPackagingDtos.stream()
            .map(ProductPackagingDto::id)
            .filter(id -> id != null)
            .toList();

        // Identificar as ProductPackaging que precisam ser removidas
        List<ProductPackaging> toRemove = existingProductPackagings.stream()
            .filter(pp -> !incomingIds.contains(pp.getId()))
            .toList();

        // Remover as ProductPackaging que não estão mais nos DTOs
        if (!toRemove.isEmpty()) {
            productPackagingRepository.deleteAll(toRemove);
        }

        // Atualizar ou criar as ProductPackaging recebidas nos DTOs
        return productPackagingDtos.stream().map(productPackagingDto -> {
            Packaging packaging = packagingService.updateOrCreate(productPackagingDto.packaging(), merchantId);

            ProductPackaging productPackaging = productPackagingDto.id() != null
                ? existingProductPackagings.stream()
                    .filter(pp -> pp.getId().equals(productPackagingDto.id()))
                    .findFirst()
                    .orElse(new ProductPackaging())
                : new ProductPackaging();

            productPackaging.setPackaging(packaging);
            productPackaging.setQuantityPerPackage(productPackagingDto.quantityPerPackage());

            return productPackagingRepository.save(productPackaging);
        }).toList();
    }

    @Transactional
    public void delete(UUID productPackagingId) {
        ProductPackaging productPackaging = productPackagingRepository.findById(productPackagingId)
                .orElseThrow(() -> new EntityNotFoundException("Relação Produto-Embalagem não encontrada."));
        productPackagingRepository.delete(productPackaging);
    }

    @Transactional
    public void deleteAll(List<ProductPackaging> productPackagings) {

        productPackagingRepository.deleteAll(productPackagings);
    }
}
