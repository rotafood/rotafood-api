package br.com.rotafood.api.application.dto.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.order.OrderPayment;
import jakarta.validation.constraints.NotNull;


public record OrderPaymentDto(
    UUID id,
    String description,
    List<OrderPaymentMethodDto> methods,
    @NotNull
    BigDecimal pending,
    @NotNull
    BigDecimal prepaid
) {
    public OrderPaymentDto(OrderPayment payment) {
    this(
        payment.getId(),
        payment.getDescription(),
        payment.getMethods() != null ? payment.getMethods().stream().map(OrderPaymentMethodDto::new).toList() : List.of(),
        payment.getPending(),
        payment.getPrepaid()
    );
}

}
