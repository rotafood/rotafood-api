package br.com.rotafood.api.domain.entity.catalog;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class DefaultPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 64)
    private String name;

    @Column(nullable = false)
    private Double lenghtCm;

    @Column(nullable = false, length = 16)
    private String widthCm;

    @Column(nullable = false)
    private Double thicknessCm;
}
