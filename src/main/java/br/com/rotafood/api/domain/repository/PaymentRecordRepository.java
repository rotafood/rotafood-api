package br.com.rotafood.api.domain.repository;

import br.com.rotafood.api.domain.entity.order.PaymentRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRecordRepository extends JpaRepository<PaymentRecord, UUID> {
}
