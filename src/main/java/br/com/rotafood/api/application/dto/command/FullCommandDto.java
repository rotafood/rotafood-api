package br.com.rotafood.api.application.dto.command;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.rotafood.api.application.dto.order.FullOrderDto;
import br.com.rotafood.api.domain.entity.command.Command;

public record FullCommandDto(
    UUID id,
    String name,
    Integer merchantSequence,
    Integer tableIndex,
    BigDecimal total,
    boolean paid,
    FullOrderDto order
) {

    public FullCommandDto(Command command) {
        this(
            command.getId(), 
            command.getName(), 
            command.getMerchantSequence(), 
            command.getTableIndex(),
            command.getTotal(),
            command.isPaid(),
            command.getOrder() != null ? new FullOrderDto(command.getOrder()) : null 
            );
    }
    
}
