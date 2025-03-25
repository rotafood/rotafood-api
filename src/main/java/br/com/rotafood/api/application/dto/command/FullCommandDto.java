package br.com.rotafood.api.application.dto.command;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.rotafood.api.application.dto.order.FullOrderDto;
import br.com.rotafood.api.domain.entity.command.Command;

public record FullCommandDto(
    UUID id,
    String name,
    Integer merchantSequence,
    Integer tableIndex,
    BigDecimal pending,
    BigDecimal prepaid,
    List<FullOrderDto> orders
) {

    public FullCommandDto(Command command) {
        this(
            command.getId(), 
            command.getName(), 
            command.getMerchantSequence(), 
            command.getTableIndex(),
            command.getPending(),
            command.getPrepaid(),
            Optional.ofNullable(command.getOrders()).map(orders -> orders.stream().map(FullOrderDto::new).toList()).orElse(List.of())
            );
    }
    
}
