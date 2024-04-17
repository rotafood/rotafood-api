package br.com.rotafood.api.domain.order.models;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.domain.merchant.models.Merchant;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private OrderType orderType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date preparationStartDateTime;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private OrderSalesChannel salesChannel;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private OrderTiming orderTiming;

    @Column(columnDefinition = "TEXT")
    private String extraInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchantId")
    private Merchant merchant;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "totalId")
    private OrderTotal total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId")
    private OrderCustomer customer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deliveryId")
    private OrderDelivery delivery;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scheduleId")
    private OrderSchedule schedule;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "indoorId")
    private OrderIndoor indoor;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "takeoutId")
    private OrderTakeout takeout;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paymentId")
    private OrderPayment payment;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderItem> items;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderBenefit> benefits;


    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderAdditionalFee> additionalFees;

}