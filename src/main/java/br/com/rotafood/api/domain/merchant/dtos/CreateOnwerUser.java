package br.com.rotafood.api.domain.merchant.dtos;

public record CreateOnwerUser (
    String email,
    String password,
    String phone
) {}
