package br.com.rotafood.api.domain.entity.order;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.catalog.CatalogContext;
import br.com.rotafood.api.domain.entity.catalog.Option;
import br.com.rotafood.api.domain.entity.catalog.OptionGroup;
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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Table(name = "order_item_options")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class OrderItemOption {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private int quantity;

    @Column(precision = 10, scale = 2)
    private BigDecimal totalPrice;


    @Enumerated(EnumType.STRING)
    @Column(length = 20)        
    private CatalogContext catalogContext;

    @ManyToOne(fetch = FetchType.LAZY)    
    @JoinColumn(name = "orderItemId")
    private OrderItem orderItem;

    @ManyToOne(fetch = FetchType.LAZY)    
    @JoinColumn(name = "optionGroupId")
    private OptionGroup optionGroup;

    @ManyToOne(fetch = FetchType.LAZY)    
    @JoinColumn(name = "optionId")
    private Option option;



}
