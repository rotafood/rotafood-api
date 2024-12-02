package br.com.rotafood.api.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rotafood.api.domain.entity.catalog.Pizza;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Long> {

    List<Pizza> findByMerchantId(UUID merchantId);

    List<Pizza> findByCategoryIdAndMerchantId(UUID categoryId, UUID merchantId);

    List<Pizza> findByIdAndCategoryIdAndMerchantId(UUID id, UUID categoryId, UUID merchantId);
}
