package br.com.rotafood.api.domain.repository;

import br.com.rotafood.api.domain.entity.order.OrderDelivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderDeliveryRepository extends JpaRepository<OrderDelivery, UUID> {
}
