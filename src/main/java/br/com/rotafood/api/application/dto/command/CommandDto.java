package br.com.rotafood.api.application.dto.command;

import java.util.UUID;

import br.com.rotafood.api.domain.entity.command.Command;

public record CommandDto(
    UUID id,
    String name,
    Integer merchantSequence,
    Integer tableIndex
) {

    public CommandDto(Command command) {
        this(
            command.getId(), 
            command.getName(), 
            command.getMerchantSequence(), 
            command.getTableIndex());
    }
    
}
