package br.com.rotafood.api.merchant.application.dto;

import java.util.UUID;

public record SortRequestDto(
    UUID id,
    Integer index
) {
    
}
