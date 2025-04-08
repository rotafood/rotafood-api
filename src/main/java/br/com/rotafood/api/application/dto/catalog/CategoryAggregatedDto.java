package br.com.rotafood.api.application.dto.catalog;

import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.AvailabilityStatus;
import br.com.rotafood.api.domain.entity.catalog.TemplateType;
import jakarta.validation.Valid;

@Valid
public record CategoryAggregatedDto(
    UUID id,
    Integer index,
    String name,
    TemplateType template,
    AvailabilityStatus status,
    List<ItemDto> items,
    List<OptionGroupDto> optionGroups
) {
}
