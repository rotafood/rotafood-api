package br.com.rotafood.api.application.dto.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.order.PaymentRecord;
import jakarta.validation.constraints.NotNull;


public record PaymentRecordDto(
    UUID id,
    String description,
    List<PaymentRecordMethodDto> methods,
    @NotNull
    BigDecimal pending,
    @NotNull
    BigDecimal prepaid
) {
    public PaymentRecordDto(PaymentRecord payment) {
        this(
            payment.getId(),
            payment.getDescription(),
            payment.getMethods() != null ? payment.getMethods().stream().map(PaymentRecordMethodDto::new).toList() : List.of(),
            payment.getPending(),
            payment.getPrepaid()
        );
    }

}
