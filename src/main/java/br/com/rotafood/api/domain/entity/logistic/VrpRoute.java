package br.com.rotafood.api.domain.entity.logistic;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vrp_routes")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class VrpRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;   

    @OneToMany(mappedBy = "vrpRoute")
    private List<VrpOrder> vrpOrders;

    @ManyToOne
    @JoinColumn(name = "vrpId")
    private Vrp vrp;

    @Column(length = 2048)
    private String googleMapsLink;


    @Column(precision = 10, scale = 2)
    private BigDecimal totalVolume;

    @Column(precision = 10, scale = 2)
    private BigDecimal distanceMeters;

    @Column
    @Temporal(TemporalType.DATE) 
    private Date createdAt;

    
    
    
}
