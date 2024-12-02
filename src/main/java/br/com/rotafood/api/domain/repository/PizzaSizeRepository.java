package br.com.rotafood.api.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotafood.api.domain.entity.catalog.PizzaSize;
import br.com.rotafood.api.domain.entity.catalog.PizzaTopping;

public interface PizzaSizeRepository extends JpaRepository<PizzaSize, UUID> {

    List<PizzaTopping> findByNameContainingIgnoreCase(String name);
}
