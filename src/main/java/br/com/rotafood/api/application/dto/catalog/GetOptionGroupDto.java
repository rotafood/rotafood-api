package br.com.rotafood.api.application.dto.catalog;

import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.OptionGroup;
import br.com.rotafood.api.domain.entity.catalog.Status;

public record GetOptionGroupDto(
    UUID id,
    String name,
    Status status,
    Integer index,
    List<OptionDto> options
) {
    public GetOptionGroupDto(Integer index, OptionGroup optionGroup) {
        this(
            optionGroup.getId(),
            optionGroup.getName(),
            optionGroup.getStatus(),
            index,
            optionGroup.getOptions().stream().map(OptionDto::new).toList()
        );
    }
}
