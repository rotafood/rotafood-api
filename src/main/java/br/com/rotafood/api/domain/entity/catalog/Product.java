package br.com.rotafood.api.domain.entity.catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.merchant.Merchant;
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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 128)
    private String name;

    @Column(nullable = true, length = 1024)
    private String description;

    @Column(length = 256)
    private String ean;

    @Column(length = 512)
    private String additionalInformation;


    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Serving serving = Serving.NOT_APPLICABLE; 
    
    private String imagePath;
    
    @Column
    @Enumerated(value = EnumType.STRING)
    private PackagingType packagingType;

    @Column
    private Integer quantity;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductPackaging> productPackagings = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOptionGroup> productOptionGroups = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchantId", nullable = false)
    private Merchant merchant;

    public void addProductPackaging(ProductPackaging packaging) {
        this.productPackagings.add(packaging);
        packaging.setProduct(this);
    }

    public void removeProductPackaging(ProductPackaging packaging) {
        this.productPackagings.remove(packaging);
        packaging.setProduct(null);
    }



    public void addProductOptionGroup(ProductOptionGroup productOptionGroup) {
        this.productOptionGroups.add(productOptionGroup);
        productOptionGroup.setProduct(this);
    }

    public void removeProductOptionGroup(ProductOptionGroup productOptionGroup) {
        this.productOptionGroups.remove(productOptionGroup);
        productOptionGroup.setProduct(null);
    }
}