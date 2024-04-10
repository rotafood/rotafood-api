package br.com.rotafood.api.domain.catalog.models;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "catalogs")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class CatalogGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Date modifiedAt;
    private UUID groupId;

    @OneToMany(mappedBy = "catalog")
    private List<Item> items;
}
