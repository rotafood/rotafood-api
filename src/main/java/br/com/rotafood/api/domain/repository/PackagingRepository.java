package br.com.rotafood.api.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotafood.api.domain.entity.catalog.Packaging;

public interface PackagingRepository extends JpaRepository<Packaging, UUID> {

    List<Packaging> findByMerchantId(UUID merchantId);

    Optional<Packaging> findByIdAndMerchantId(UUID id, UUID merchantId);
}