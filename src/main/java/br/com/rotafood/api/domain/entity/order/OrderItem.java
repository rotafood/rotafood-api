package br.com.rotafood.api.domain.entity.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.Item;
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


@Table(name = "orderItems")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private int quantity;

    @Column(precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @OneToMany(mappedBy = "orderItem")
    private List<OrderItemOption> options;

    @ManyToOne(fetch = FetchType.LAZY)    
    @JoinColumn(name = "itemId")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)    
    @JoinColumn(name = "orderId")
    private Order order;

}
