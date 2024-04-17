package br.com.rotafood.api.domain.catalog.models;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.domain.merchant.models.Merchant;
import br.com.rotafood.api.domain.storage.models.Image;
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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductDietaryRestrictions dietaryRestrictions;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WeightUnit weightUnit;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal weightQuantity;

    @OneToOne
    @JoinColumn(name = "imageId")
    private Image image;

    @OneToOne
    @JoinColumn(name = "productSellingOptionId")
    private ProductSellingOption sellingOption;

    @OneToMany(mappedBy = "product")
    private List<ProductOptionGroup> optionGroups;

    @OneToOne(mappedBy = "product")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchantId")
    private Merchant merchant;




}
