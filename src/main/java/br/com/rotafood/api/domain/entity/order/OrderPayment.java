package br.com.rotafood.api.domain.entity.order;

import java.math.BigDecimal;
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
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class OrderPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 1024)
    private String description;

    
    @Column(precision = 10, scale = 2)
    private BigDecimal pending;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal prepaid;
    
    @OneToMany(mappedBy = "payment")
    private List<OrderPaymentMethod> methods;
    
    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;

}

