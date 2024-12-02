package br.com.rotafood.api.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotafood.api.domain.entity.catalog.PizzaCrush;

public interface PizzaCrushRepository extends JpaRepository<PizzaCrush, UUID> {

    List<PizzaCrush> findByNameContainingIgnoreCase(String name);
}
