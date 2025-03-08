package br.com.rotafood.api.domain.entity.catalog;

import java.util.Date;
import java.util.Set;
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
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "catalogs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Catalog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private Date modifiedAt;

    @Column
    @Enumerated(EnumType.STRING)
    private AvailabilityStatus status;

    @Column
    @Enumerated(EnumType.STRING)
    private CatalogContext catalogContext;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchantId") 
    private Merchant merchant;

    @OneToMany(mappedBy = "catalog", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CatalogCategory> catalogCategories;

        
        
    @Column(nullable = true)
    private UUID iFoodCatalogId;
}
