package br.com.rotafood.api.domain.repository;

import br.com.rotafood.api.domain.entity.order.OrderCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderCustomerRepository extends JpaRepository<OrderCustomer, UUID> {
    List<OrderCustomer> findAllByMerchantId(UUID merchantId);
    Optional<OrderCustomer> findByIdAndMerchantId(UUID id, UUID merchantId);
}
