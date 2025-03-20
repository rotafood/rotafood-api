package br.com.rotafood.api.domain.entity.command;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.merchant.Merchant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "tables")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class DinnerTable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;    

    @Column(nullable = false)
    private Integer number;

    @Column(precision = 10, scale = 2)
    private BigDecimal pending;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal prepaid;

    @OneToMany(mappedBy = "dinnerTable")
    private List<Command> commands;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchantId") 
    private Merchant merchant;

}
