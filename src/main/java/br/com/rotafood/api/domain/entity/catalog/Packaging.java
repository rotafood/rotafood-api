package br.com.rotafood.api.domain.entity.catalog;

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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "packagings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Packaging {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 64)
    private String name;

    @Column(nullable = false)
    private Double lenghtCm;

    @Column(nullable = false, length = 16)
    private String widthCm;

    @Column(nullable = false)
    private Double thicknessCm; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchantId", nullable = false)
    private Merchant merchant;
}
