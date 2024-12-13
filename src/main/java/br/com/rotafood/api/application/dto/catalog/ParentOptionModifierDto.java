package br.com.rotafood.api.application.dto.catalog;

import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.ParentOptionModifier;
import br.com.rotafood.api.domain.entity.catalog.Status;

public record ParentOptionModifierDto (
    UUID id,
    UUID optionId,
    Status status,
    String name
) {
    public ParentOptionModifierDto(ParentOptionModifier parentOption) {
        this(
            parentOption.getId(),
            parentOption.getId(),
            parentOption.getOption().getStatus(),
            parentOption.getOption().getProduct().getName()
        );
    }
}
