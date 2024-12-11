package br.com.rotafood.api.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotafood.api.domain.entity.catalog.Image;


public interface ImageRepository extends JpaRepository<Image, UUID> {

    List<Image> findAllByMerchantId(UUID merchantId);

    Image findByIdAndMerchantId(UUID id, UUID merchantId);

}
