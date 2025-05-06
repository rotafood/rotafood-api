package br.com.rotafood.api.merchant.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotafood.api.merchant.domain.entity.Image;


public interface ImageRepository extends JpaRepository<Image, UUID> {

    List<Image> findAllByMerchantId(UUID merchantId);

    Image findByIdAndMerchantId(UUID id, UUID merchantId);

}
