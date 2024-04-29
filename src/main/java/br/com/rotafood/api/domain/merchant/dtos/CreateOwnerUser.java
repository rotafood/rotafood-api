package br.com.rotafood.api.domain.merchant.dtos;

public record CreateOwnerUser (
    String name,
    String email,
    String password,
    String phone,
    String document
) {}
