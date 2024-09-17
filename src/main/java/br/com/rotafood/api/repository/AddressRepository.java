package br.com.rotafood.api.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotafood.api.domain.address.Address;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
}

