package br.com.rotafood.api.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.rotafood.api.domain.entity.catalog.Category;

public interface CategoryRepository extends JpaRepository<Category, UUID>  {

    List<Category> findByMerchantId(UUID merchantId);
    
    Category findByIdAndMerchantId(UUID id, UUID merchantId);

    @Modifying
    @Query("UPDATE Category c SET c.index = :newIndex WHERE c.id = :categoryId AND c.merchant.id = :merchantId")
    void updateCategoryIndex(UUID merchantId, UUID categoryId, int newIndex);

}