package br.com.rotafood.api.application.dto.order;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.order.PaymentRecordMethod;
import br.com.rotafood.api.domain.entity.order.PaymentRecordMethodType;
import br.com.rotafood.api.domain.entity.order.PaymentRecordType;
import jakarta.validation.constraints.NotNull;

public record PaymentRecordMethodDto(
    UUID id,
    String description,
    @NotNull
    PaymentRecordMethodType method,
    @NotNull
    boolean paid,
    PaymentRecordType type,
    BigDecimal value,
    BigDecimal changeFor
) {
    public PaymentRecordMethodDto(PaymentRecordMethod method) {
    this(
        method.getId(),
        method.getDescription(),
        method.getMethod(),
        method.isPaid(),
        method.getType(),
        method.getValue(),
        method.getChangeFor()
    );
}

}
