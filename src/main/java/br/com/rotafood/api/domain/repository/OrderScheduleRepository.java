package br.com.rotafood.api.domain.repository;

import br.com.rotafood.api.domain.entity.order.OrderSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderScheduleRepository extends JpaRepository<OrderSchedule, UUID> {
}
