package br.com.rotafood.api.application.dto.catalog;

import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.OptionGroup;
import br.com.rotafood.api.domain.entity.catalog.Status;

public record OptionGroupDto(
    UUID id,
    String name,
    Status status,
    List<OptionDto> options
) {
    public OptionGroupDto(OptionGroup optionGroup) {
        this(
            optionGroup.getId(),
            optionGroup.getName(),
            optionGroup.getStatus(),
            optionGroup.getOptions().stream().map(OptionDto::new).toList()
        );
    }
}
