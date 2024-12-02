package br.com.rotafood.api.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotafood.api.domain.entity.catalog.PizzaEdge;

public interface PizzaEdgeRepository extends JpaRepository<PizzaEdge, UUID> {

    List<PizzaEdge> findByNameContainingIgnoreCase(String name);
}
