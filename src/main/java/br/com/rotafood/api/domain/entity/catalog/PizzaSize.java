package br.com.rotafood.api.domain.entity.catalog;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "pizza_sizes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class PizzaSize {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private Integer index;

    private Status status;

    private Integer slices;

    @Column(name = "tags", columnDefinition = "integer[]")
    private List<Integer> acceptedFractions;

    @OneToOne
    @JoinColumn(name = "priceId") 
    private Price price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pizzaId") 
    private Pizza pizza;



}
