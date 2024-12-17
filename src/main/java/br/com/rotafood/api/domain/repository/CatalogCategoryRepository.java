package br.com.rotafood.api.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotafood.api.domain.entity.catalog.CatalogCategory;

public interface CatalogCategoryRepository extends JpaRepository<CatalogCategory, UUID>  {

    void deleteByCatalogId(UUID id);

    void deleteByCategoryId(UUID id);

}