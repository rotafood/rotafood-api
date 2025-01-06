package br.com.rotafood.api.domain.entity.logistic;

import java.util.UUID;

import br.com.rotafood.api.domain.entity.order.Order;
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
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vrp_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class VrpOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;  

    @ManyToOne(fetch = FetchType.LAZY)    
    @JoinColumn(name = "vrpRouteId")
    private VrpRoute vrpRoute;

    @Column
    private int index;
    
    @ManyToOne(fetch = FetchType.LAZY)    
    @JoinColumn(name = "orderId")
    private Order order;
    
    
}
