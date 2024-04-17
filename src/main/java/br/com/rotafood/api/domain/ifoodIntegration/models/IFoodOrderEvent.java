package br.com.rotafood.api.domain.ifoodIntegration.models;


import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ifoodEvents")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class IFoodOrderEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    private String code;

    private UUID orderId;
}
