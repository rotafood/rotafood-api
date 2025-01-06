package br.com.rotafood.api.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rotafood.api.application.dto.catalog.ProductPackagingDto;
import br.com.rotafood.api.domain.entity.catalog.ProductPackaging;
import br.com.rotafood.api.domain.entity.catalog.Packaging;
import br.com.rotafood.api.domain.entity.catalog.Product;
import br.com.rotafood.api.domain.repository.ProductPackagingRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductPackagingService {


    @Autowired 
    private PackagingService packagingService;
    @Autowired 
    private ProductPackagingRepository productPackagingRepository;


    @Transactional
    public List<ProductPackaging> createOrUpdateAll(List<ProductPackagingDto> productPackagingDtos, Product product, UUID merchantId) {
        if (productPackagingDtos == null || productPackagingDtos.isEmpty()) {
            List<ProductPackaging> toRemove = new ArrayList<>(product.getProductPackagings());
            product.getProductPackagings().clear();
            this.deleteAll(toRemove);
            return List.of();
        }

        List<UUID> incomingIds = productPackagingDtos.stream()
            .map(ProductPackagingDto::id)
            .filter(Objects::nonNull)
            .toList();


        List<ProductPackaging> toRemove = product.getProductPackagings().stream()
            .filter(pp -> !incomingIds.contains(pp.getId()))
            .toList();

        product.getProductPackagings().removeAll(toRemove);
        this.deleteAll(toRemove);

        List<ProductPackaging> updatedProductPackagings = productPackagingDtos.stream().map(productPackagingDto -> {
            Packaging packaging = packagingService.updateOrCreate(productPackagingDto.packaging(), merchantId);

            ProductPackaging productPackaging = productPackagingDto.id() != null
                ? productPackagingRepository.findById(productPackagingDto.id())
                    .orElse(new ProductPackaging())
                : new ProductPackaging();

            productPackaging.setPackaging(packaging);
            productPackaging.setProduct(product);
            productPackaging.setQuantityPerPackage(productPackagingDto.quantityPerPackage());

            return productPackagingRepository.save(productPackaging);
        }).toList();

        updatedProductPackagings.forEach(updated -> {
            if (!product.getProductPackagings().contains(updated)) {
                product.getProductPackagings().add(updated);
            }
        });

        return updatedProductPackagings;
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
