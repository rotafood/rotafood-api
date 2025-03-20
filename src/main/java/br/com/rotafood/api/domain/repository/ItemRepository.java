package br.com.rotafood.api.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.rotafood.api.domain.entity.catalog.Item;

public interface ItemRepository extends JpaRepository<Item, UUID> {

    Item findByIdAndMerchantId(UUID id, UUID merchantId);

    List<Item> findAllByMerchantId(UUID merchantId);

    Item findByIdAndProductIdAndCategoryIdAndMerchantId(UUID id, UUID productId, UUID categoryId, UUID merchantId);

    void deleteByCategoryId(UUID categoryId);

    @Modifying
    @Query("UPDATE Item i SET i.index = :newIndex WHERE i.id = :itemId AND i.category.id = :categoryId")
    void updateItemIndex(UUID categoryId, UUID itemId, int newIndex);


}
 