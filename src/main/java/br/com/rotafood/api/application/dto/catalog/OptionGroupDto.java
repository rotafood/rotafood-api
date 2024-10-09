package br.com.rotafood.api.application.dto.catalog;

import java.util.List;
import java.util.UUID;

public record OptionGroupDto(
    UUID id,
    String name,
    String status,
    String externalCode,
    Integer index,
    String optionGroupType,
    List<UUID> optionIds
) {

}
