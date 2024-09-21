package br.com.rotafood.api.domain.entity.order;

import java.util.UUID;

import jakarta.persistence.Column;
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
@Table(name = "order_customers")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class OrderCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private int ordersCountOnMerchant;

    @Column(length = 20)
    private String segmentation;

    @Column(length = 20)
    private String name;

    @Column(length = 20)
    private String document;

    @Column(length = 20)
    private String phone;

    @OneToOne
    @JoinColumn(name = "orderId")
    private Order order;

}
