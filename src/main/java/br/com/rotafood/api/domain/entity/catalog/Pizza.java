package br.com.rotafood.api.domain.entity.catalog;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.merchant.Merchant;


@Entity
@Table(name = "pizzas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany(mappedBy = "pizza")
    private List<PizzaSize> sizes;
    
    @OneToMany(mappedBy = "pizza")
    private List<PizzaCrush> crusts;
    
    @OneToMany(mappedBy = "pizza")
    private List<PizzaEdge> edges;
    
    @OneToMany(mappedBy = "pizza")
    private List<PizzaTopping> toppings;
    
    @OneToMany(mappedBy = "pizza")
    private List<Shift> shifts;
    
    @OneToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchantId")
    private Merchant merchant;

    private UUID iFoodPizzaId;

}