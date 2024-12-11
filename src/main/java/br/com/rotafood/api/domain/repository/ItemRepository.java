package br.com.rotafood.api.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotafood.api.domain.entity.catalog.Item;

public interface ItemRepository extends JpaRepository<Item, UUID> {

    Item findByIdAndMerchantId(UUID id, UUID merchantId);

    List<Item> getAllByMerchantId(UUID merchantId);

    Item findByIdAndProductIdAndCategoryIdAndMerchantId(UUID id, UUID productId, UUID categoryId, UUID merchantId);


}
 