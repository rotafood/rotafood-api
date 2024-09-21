package br.com.rotafood.api.domain.repository;


import br.com.rotafood.api.domain.entity.catalog.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
