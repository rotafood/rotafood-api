package br.com.rotafood.api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotafood.api.domain.catalog.Catalog;

public interface CatalogRepository extends JpaRepository<Catalog, UUID>  {

}
