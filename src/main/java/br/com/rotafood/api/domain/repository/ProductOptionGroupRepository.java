package br.com.rotafood.api.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotafood.api.domain.entity.catalog.ProductOptionGroup;

public interface ProductOptionGroupRepository extends JpaRepository<ProductOptionGroup, UUID> {

    ProductOptionGroup findByIdAndOptionGroupId(UUID id, UUID optionGroupId);

    ProductOptionGroup findByProductIdAndOptionGroupId(UUID productId, UUID optionGroupId);

    List<ProductOptionGroup> findAllByProductId(UUID productId);

}
