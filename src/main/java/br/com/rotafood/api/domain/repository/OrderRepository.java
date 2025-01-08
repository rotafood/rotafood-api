package br.com.rotafood.api.domain.repository;

import br.com.rotafood.api.domain.entity.order.Order;
import br.com.rotafood.api.domain.entity.order.OrderType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findAllByMerchantId(UUID merchantId);
    Optional<Order> findByIdAndMerchantId(UUID id, UUID merchantId);

    @Query("SELECT o FROM Order o " +
           "WHERE o.merchant.id = :merchantId " +
           "AND (:orderTypes IS NULL OR o.type IN :orderTypes) " +
           "AND (:isCompleted IS NULL OR " +
           "     (CASE WHEN :isCompleted = true THEN o.status = 'COMPLETED' ELSE o.status <> 'COMPLETED' END))")
    List<Order> findAllByFilters(
            @Param("merchantId") UUID merchantId,
            @Param("orderTypes") List<OrderType> orderTypes,
            @Param("isCompleted") Boolean isCompleted);
}
