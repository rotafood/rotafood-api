package br.com.rotafood.api.domain.entity.catalog;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;
    
    @Column
    private Integer index;

    @Column
    @Enumerated(EnumType.STRING)
    private ItemType type;

    @OneToOne
    @JoinColumn(name = "priceId")
    private Price price;

    @OneToMany(mappedBy = "item")
    private List<Shift> shifts;
    
    
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContextModifier> contextModifiers;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemOptionGroup> itemOptionGroups;
    
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;
    
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;
    
    @Column(nullable = true)
    private UUID iFoodItemId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchantId")
    private Merchant merchant;

}
