package br.com.rotafood.api.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rotafood.api.application.dto.catalog.ProductPackagingDto;
import br.com.rotafood.api.domain.entity.catalog.Product;
import br.com.rotafood.api.domain.entity.catalog.ProductPackaging;
import br.com.rotafood.api.domain.entity.catalog.Packaging;
import br.com.rotafood.api.domain.repository.ProductRepository;
import br.com.rotafood.api.domain.repository.ProductPackagingRepository;
import br.com.rotafood.api.domain.repository.PackagingRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductPackagingService {

    @Autowired private ProductRepository productRepository;
    @Autowired private PackagingRepository packagingRepository;
    @Autowired private ProductPackagingRepository productPackagingRepository;


    public List<ProductPackagingDto> getAllByProductId(UUID productId) {
        return productPackagingRepository.findByProductId(productId)
                .stream()
                .map(ProductPackagingDto::new)
                .toList();
    }

    @Transactional
    public ProductPackaging updateOrCreate(ProductPackagingDto productPackagingDto, UUID productId) {

        Packaging packaging = packagingRepository.findById(productPackagingDto.packaging().id())
                .orElseThrow(() -> new EntityNotFoundException("Embalagem não encontrada."));


        ProductPackaging productPackaging = productPackagingDto.id() != null
            ? productPackagingRepository.findById(productPackagingDto.id())
                .orElseThrow(() -> new EntityNotFoundException("Relação Produto-Embalagem não encontrada."))
            : new ProductPackaging(null, productPackagingDto.quantityPerPackage(), null, packaging);
        
        productPackaging.setQuantityPerPackage(productPackagingDto.quantityPerPackage());
        
        if (productId != null) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado."));
            productPackaging.setProduct(product);
        }

        return productPackagingRepository.save(productPackaging);
    }

    @Transactional
    public void delete(UUID productPackagingId) {
        ProductPackaging productPackaging = productPackagingRepository.findById(productPackagingId)
                .orElseThrow(() -> new EntityNotFoundException("Relação Produto-Embalagem não encontrada."));
        productPackagingRepository.delete(productPackaging);
    }
}
