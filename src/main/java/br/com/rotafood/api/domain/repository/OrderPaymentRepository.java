package br.com.rotafood.api.domain.repository;

import br.com.rotafood.api.domain.entity.order.OrderPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderPaymentRepository extends JpaRepository<OrderPayment, UUID> {
}
