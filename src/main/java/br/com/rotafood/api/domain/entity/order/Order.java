package br.com.rotafood.api.domain.entity.order;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.com.rotafood.api.domain.entity.merchant.Merchant;
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
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant modifiedAt;

    @Column(nullable = false)
    private Instant preparationStartDateTime;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private OrderSalesChannel salesChannel;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private OrderTiming timing;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private OrderType type;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private OrderStatus status;

    @Column(columnDefinition = "TEXT")
    private String extraInfo;

    @ManyToOne(fetch = FetchType.LAZY)    
    @JoinColumn(name = "merchantId")
    private Merchant merchant;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "orderTotalId")
    private OrderTotal total;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "orderCustomerId")
    private OrderCustomer customer;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "orderDeliveryId")
    private OrderDelivery delivery;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "orderScheduleId")
    private OrderSchedule schedule;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "orderIndoorId")
    private OrderIndoor indoor;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "orderTakeoutId")
    private OrderTakeout takeout;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "orderPaymentId")
    private OrderPayment payment;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderBenefit> benefits;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderAdditionalFee> additionalFees;

    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }

    public void removeItem(OrderItem item) {
        items.remove(item);
        item.setOrder(null);
    }

    public void addBenefit(OrderBenefit benefit) {
        benefits.add(benefit);
        benefit.setOrder(this);
    }

    public void removeBenefit(OrderBenefit benefit) {
        benefits.remove(benefit);
        benefit.setOrder(null);
    }

    public void addAdditionalFee(OrderAdditionalFee additionalFee) {
        additionalFees.add(additionalFee);
        additionalFee.setOrder(this);
    }

    public void removeAdditionalFee(OrderAdditionalFee additionalFee) {
        additionalFees.remove(additionalFee);
        additionalFee.setOrder(null);
    }

}