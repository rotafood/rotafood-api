package br.com.rotafood.api.domain.repository;

import br.com.rotafood.api.domain.entity.order.OrderTakeout;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderTakeoutRepository extends JpaRepository<OrderTakeout, UUID> {
}
