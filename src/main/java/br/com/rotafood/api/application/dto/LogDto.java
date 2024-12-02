package br.com.rotafood.api.application.dto;

import java.sql.Date;

public record LogDto(
    Date date,
    String location,
    String url
) {
    
}
