package br.com.rotafood.api.domain.ifoodIntegration.models;

import java.util.UUID;

import br.com.rotafood.api.domain.catalog.models.Catalog;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ifood_catalogs")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class IFoodCatalog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID ifoodId;

    private String acessToken;

    private String refreshToken;

    @OneToOne
    @JoinColumn(name = "catalog_id")
    private Catalog catalog;
    
}
