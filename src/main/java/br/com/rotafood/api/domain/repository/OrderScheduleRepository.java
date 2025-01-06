package br.com.rotafood.api.domain.repository;

import br.com.rotafood.api.domain.entity.order.OrderSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderScheduleRepository extends JpaRepository<OrderSchedule, UUID> {
    List<OrderSchedule> findAllByMerchantId(UUID merchantId);
    Optional<OrderSchedule> findByIdAndMerchantId(UUID id, UUID merchantId);
}
