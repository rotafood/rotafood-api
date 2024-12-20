package br.com.rotafood.api.domain.entity.merchant;

import java.util.UUID;

import br.com.rotafood.api.domain.entity.address.Address;
import br.com.rotafood.api.domain.entity.catalog.Shift;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "merchants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 64)
    private String name;
    
    @Column(length = 64)
    private String corporateName;
    
    private String onlineName;

    @Column(length = 256)
    private String description;

    @Enumerated(value = EnumType.STRING)
    private DocumentType documentType;

    @Column(length = 16)
    private String document;

    @Column(nullable = false)
    private MerchantType merchantType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdAt;

    private String imagePath;

    @OneToOne
    @JoinColumn(name = "addressId")
    private Address address;
    

    @OneToMany(mappedBy = "merchant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MerchantUser> merchantUsers;

    @OneToMany(mappedBy = "merchant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Shift> openingHours = new ArrayList<>();
}
