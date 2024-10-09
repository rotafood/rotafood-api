package br.com.rotafood.api.domain.entity.catalog;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.merchant.Merchant;
import br.com.rotafood.api.domain.entity.storage.Image;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 128)
    private String name;

    @Column(nullable = false, length = 1024)
    private String description;

    @Column(nullable = false, length = 256)
    private String ean;

    @Column(nullable = false, length = 512)
    private String additionalInformation;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ProductType productType;

    @ElementCollection(targetClass = ProductDietaryRestrictions.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "product_dietary_restrictions", joinColumns = @JoinColumn(name = "productId"))
    @Enumerated(EnumType.STRING)
    @Column(name = "restriction", nullable = false)
    private List<ProductDietaryRestrictions> dietaryRestrictions;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WeightUnit weightUnit;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal weightQuantity;
    
    @OneToOne(mappedBy = "product")
    private Item item;

    @OneToOne(mappedBy = "product")
    private Option option;
    
    @OneToOne(mappedBy = "product")
    private ProductSellingOption sellingOption;

    @OneToMany(mappedBy = "product")
    private List<OptionGroup> optionGroups;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchantId")
    private Merchant merchant;

    @ManyToOne
    @JoinColumn(name = "imageId")
    private Image image;

}
