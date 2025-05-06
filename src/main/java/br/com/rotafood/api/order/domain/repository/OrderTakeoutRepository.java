package br.com.rotafood.api.order.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotafood.api.order.domain.entity.OrderTakeout;

import java.util.UUID;

public interface OrderTakeoutRepository extends JpaRepository<OrderTakeout, UUID> {
}
