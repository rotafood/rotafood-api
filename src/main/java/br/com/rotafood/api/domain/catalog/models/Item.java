package br.com.rotafood.api.domain.catalog.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "items")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String type;
    private UUID categoryId;
    private String status;
    
    @OneToOne(mappedBy = "item", cascade = CascadeType.ALL)
    private Price price;

    private String externalCode;
    private Integer index;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @OneToMany(mappedBy = "item")
    private List<OptionGroup> optionGroups;

    @OneToMany(mappedBy = "item")
    private List<Shift> shifts;

    

}
