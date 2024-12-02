package br.com.rotafood.api.domain.entity.catalog;

import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.merchant.Merchant;
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
import jakarta.persistence.OneToOne;
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

    @Column(nullable = false, length = 1024)
    private String description;

    @Column(nullable = false, length = 256)
    private String ean;

    @Column(nullable = false, length = 512)
    private String additionalInformation;

    @Column(name = "dietary_restrictions", columnDefinition = "text[]")
    private List<String> dietaryRestrictions;

    @OneToOne(mappedBy = "product")
    private SellingOption sellingOption;
    
    @OneToOne(mappedBy = "product")
    private Item item;

    @OneToOne(mappedBy = "product")
    private Option option;
    
    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
    private Weight weight;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Serving serving; 
    
    @Column(name = "tags", columnDefinition = "text[]")
    private List<String> tags;
    
    private String imagePath;
    
    @Column(name = "multipleImages", columnDefinition = "text[]")
    private List<String> multipleImages;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchantId")
    private Merchant merchant;
}
