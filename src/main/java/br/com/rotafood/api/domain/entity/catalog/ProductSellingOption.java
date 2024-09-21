package br.com.rotafood.api.domain.entity.catalog;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "product_option_groups")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ProductSellingOption {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "UUID")
    private UUID id;

    @Column(nullable = false)
    private Integer minimum;

    @Column(nullable = false)
    private Integer incremental;

    @Column(nullable = false, length = 32)
    private List<String> availableUnits;

    private Integer averageUnit;

    @OneToOne
    @JoinColumn(name = "productId")
    private Product product;
}
