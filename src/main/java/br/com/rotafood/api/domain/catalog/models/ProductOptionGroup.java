package br.com.rotafood.api.domain.catalog.models;

import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.domain.merchant.models.Merchant;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_option_groups")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ProductOptionGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String status;
    private String externalCode;
    private Integer index;
    private String optionGroupType;

    // @ManyToOne
    // @JoinColumn(name = "productId", referencedColumnName = "id")
    // private Product product;

    // @ManyToOne
    // @JoinColumn(name = "itemId")
    // private Item item;
    
    @OneToMany(mappedBy = "productOptionGroup")
    private List<ProductOption> options;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchantId")
    private Merchant merchant;
}