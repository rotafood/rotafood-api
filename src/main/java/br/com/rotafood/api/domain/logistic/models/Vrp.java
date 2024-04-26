package br.com.rotafood.api.domain.logistic.models;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.domain.merchant.models.Merchant;
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
@Table(name = "vrps")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Vrp {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;   


    @ManyToOne
    @JoinColumn(name = "merhcantId")
    private Merchant merchant;

    @OneToMany(mappedBy = "vrp")
    private List<VrpRoute> routes;

    @Column
    @Temporal(TemporalType.DATE) 
    private Date createdAt;


    @Column
    @Temporal(TemporalType.DATE) 
    private Date solvedAt;
    
    
}
