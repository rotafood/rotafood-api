package br.com.rotafood.api.domain.repository;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotafood.api.domain.entity.address.Address;

public interface AddressRepository extends JpaRepository<Address, UUID> {
    Optional<Address> findByPostalCode(String postalCode);
}

