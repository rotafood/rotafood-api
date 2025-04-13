package br.com.rotafood.api.application.dto.command;

import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.application.dto.order.FullOrderDto;
import br.com.rotafood.api.domain.entity.command.Command;
import br.com.rotafood.api.domain.entity.command.CommandStatus;
import br.com.rotafood.api.domain.entity.order.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record FullCommandDto(
    UUID id,
    @NotNull String name,
    Integer merchantSequence,
    Integer tableIndex,
    @NotNull CommandStatus status,
    List<FullOrderDto> orders

) {
    public FullCommandDto(Command command) {
    this(
        command.getId(),
        command.getName(),
        command.getMerchantSequence(),
        command.getTableIndex(),
        command.getStatus(),
        command.getOrders() != null
            ? command.getOrders().stream()
                .filter(order -> order.getStatus() != OrderStatus.COMPLETED && order.getStatus() != OrderStatus.CANCELED)
                .map(FullOrderDto::new)
                .toList()
            : null
    );
}
}
