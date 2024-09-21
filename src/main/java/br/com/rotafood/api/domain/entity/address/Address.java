package br.com.rotafood.api.domain.entity.address;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.rotafood.api.application.dto.address.AddressDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "addresses")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String country;
    private String state;
    private String city;
    private String neighborhood;
    private String postalCode;
    private String streetName;
    private String streetNumber;
    private String formattedAddress;
    private String complement;
    @Column(scale = 6, precision = 9)
    private BigDecimal latitude;
    @Column(scale = 6, precision = 9)
    private BigDecimal longitude;


    public Address(AddressDto dto) {
        this.country = dto.country();
        this.state = dto.state();
        this.city = dto.city();
        this.neighborhood = dto.neighborhood();
        this.postalCode = dto.postalCode();
        this.streetName = dto.streetName();
        this.streetNumber = dto.streetNumber();
        this.formattedAddress = dto.formattedAddress();
        this.complement = dto.complement();
        this.latitude = dto.latitude();
        this.longitude = dto.longitude();
    }

}
