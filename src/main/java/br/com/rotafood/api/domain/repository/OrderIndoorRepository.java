package br.com.rotafood.api.domain.repository;

import br.com.rotafood.api.domain.entity.order.OrderIndoor;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.UUID;

public interface OrderIndoorRepository extends JpaRepository<OrderIndoor, UUID> {
}
