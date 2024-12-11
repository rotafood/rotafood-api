package br.com.rotafood.api.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotafood.api.domain.entity.catalog.Category;

public interface CategoryRepository extends JpaRepository<Category, UUID>  {

    List<Category> findByMerchantId(UUID merchantId);
    
    Category findByIdAndMerchantId(UUID id, UUID merchantId);

}