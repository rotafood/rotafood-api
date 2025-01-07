package br.com.rotafood.api.domain.repository;

import br.com.rotafood.api.domain.entity.order.OrderTotal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderTotalRepository extends JpaRepository<OrderTotal, UUID> {
}
