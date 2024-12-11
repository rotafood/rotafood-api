package br.com.rotafood.api.application.dto.catalog;

import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.ItemOptionGroup;
import br.com.rotafood.api.domain.entity.catalog.Status;

public record GetOptionGroupDto(
    UUID id,
    String name,
    Status status,
    Integer min,
    Integer max,
    Integer index,
    List<OptionDto> options
) {
    public GetOptionGroupDto(ItemOptionGroup itemOptionGroup) {
        this(
            itemOptionGroup.getOptionGroup().getId(),
            itemOptionGroup.getOptionGroup().getName(),
            itemOptionGroup.getOptionGroup().getStatus(),
            itemOptionGroup.getMin(),
            itemOptionGroup.getMax(),
            itemOptionGroup.getIndex(),
            itemOptionGroup.getOptionGroup().getOptions().stream().map(OptionDto::new).toList()
        );
    }
}
