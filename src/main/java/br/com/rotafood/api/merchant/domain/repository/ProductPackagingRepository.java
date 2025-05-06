package br.com.rotafood.api.merchant.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotafood.api.catalog.domain.entity.ProductPackaging;

public interface ProductPackagingRepository extends JpaRepository<ProductPackaging, UUID> {
    List<ProductPackaging> findByPackagingId(UUID packagingId);
}
