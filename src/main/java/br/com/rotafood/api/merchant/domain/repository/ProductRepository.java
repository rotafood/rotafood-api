package br.com.rotafood.api.merchant.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotafood.api.catalog.domain.entity.Product;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    List<Product> findAllByMerchantId(UUID merchantId);

    Product findByIdAndMerchantId(UUID id, UUID merchantId);

}
