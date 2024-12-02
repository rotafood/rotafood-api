package br.com.rotafood.api.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotafood.api.domain.entity.catalog.Item;

public interface ItemRepository extends JpaRepository<Item, UUID> {

    List<Item> findByIdAndMerchantId(UUID id, UUID merchantId);


}
