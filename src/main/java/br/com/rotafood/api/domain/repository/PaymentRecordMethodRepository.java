package br.com.rotafood.api.domain.repository;

import br.com.rotafood.api.domain.entity.order.PaymentRecordMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRecordMethodRepository extends JpaRepository<PaymentRecordMethod, UUID> {
}
