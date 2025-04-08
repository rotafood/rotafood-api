package br.com.rotafood.api.application.dto.order;

import br.com.rotafood.api.domain.entity.order.PaymentRecordMethod;
import br.com.rotafood.api.domain.entity.order.PaymentRecordMethodType;
import br.com.rotafood.api.domain.entity.order.PaymentRecordType;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentRecordMethodDto(
    UUID id,
    String description,
    PaymentRecordMethodType method,
    boolean prepaid,
    PaymentRecordType type,
    BigDecimal value,
    BigDecimal changeFor
) {
    public PaymentRecordMethodDto(PaymentRecordMethod method) {
    this(
        method.getId(),
        method.getDescription(),
        method.getMethod(),
        method.isPrepaid(),
        method.getType(),
        method.getValue(),
        method.getChangeFor()
    );
}

}
