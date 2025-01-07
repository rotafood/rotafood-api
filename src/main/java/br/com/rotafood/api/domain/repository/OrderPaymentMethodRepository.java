package br.com.rotafood.api.domain.repository;

import br.com.rotafood.api.domain.entity.order.OrderPaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderPaymentMethodRepository extends JpaRepository<OrderPaymentMethod, UUID> {
}
