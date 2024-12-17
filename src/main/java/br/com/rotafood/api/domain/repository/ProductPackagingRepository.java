package br.com.rotafood.api.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotafood.api.domain.entity.catalog.ProductPackaging;

public interface ProductPackagingRepository extends JpaRepository<ProductPackaging, UUID> {

    List<ProductPackaging> findByProductId(UUID productId);

    List<ProductPackaging> findByPackagingId(UUID packagingId);


}
