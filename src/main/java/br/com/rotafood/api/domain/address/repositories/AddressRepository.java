package br.com.rotafood.api.domain.address.repositories;
import br.com.rotafood.api.domain.address.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {}

