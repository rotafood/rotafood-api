package br.com.rotafood.api.application.dto;

import java.util.UUID;

public record SortRequestDto(
    UUID id,
    Integer index
) {
    
}
