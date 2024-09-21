package br.com.rotafood.api.domain.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotafood.api.domain.entity.catalog.Catalog;

public interface CatalogRepository extends JpaRepository<Catalog, UUID>  {

}
