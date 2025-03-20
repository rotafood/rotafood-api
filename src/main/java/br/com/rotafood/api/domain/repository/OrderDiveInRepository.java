package br.com.rotafood.api.domain.repository;

import br.com.rotafood.api.domain.entity.order.OrderDiveIn;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.UUID;

public interface OrderDiveInRepository extends JpaRepository<OrderDiveIn, UUID> {
}
