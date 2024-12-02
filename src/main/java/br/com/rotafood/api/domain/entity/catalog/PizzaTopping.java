package br.com.rotafood.api.domain.entity.catalog;

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
@Table(name = "pizza_toppings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class PizzaTopping {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String description;

    private String imagePath;

    private List<String> dietaryRestrictions;

    private Integer index;

    private Status status;

    @OneToOne
    @JoinColumn(name = "priceId") 
    private Price price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pizzaId") 
    private Pizza pizza;


}
