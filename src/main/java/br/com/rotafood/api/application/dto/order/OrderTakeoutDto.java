package br.com.rotafood.api.application.dto.order;

import java.util.Date;
import java.util.UUID;

public record OrderTakeoutDto(
    UUID id,
    Date takeoutDateTime,
    String mode,
    String comments
) {
}

