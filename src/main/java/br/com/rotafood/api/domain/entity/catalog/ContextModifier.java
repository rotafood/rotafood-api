package br.com.rotafood.api.domain.entity.catalog;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Entity
@Table(name = "context_modifiers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ContextModifier {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "priceId", nullable = true)
    private Price price;

    @ManyToOne
    @JoinColumn(name = "itemId", nullable = true)
    private Item item;

    @ManyToOne
    @JoinColumn(name = "optionId", nullable = true)
    private Option option;

    @Enumerated(EnumType.STRING)
    private CatalogContext catalogContext;
}
