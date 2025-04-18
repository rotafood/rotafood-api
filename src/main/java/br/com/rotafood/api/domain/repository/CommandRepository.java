package br.com.rotafood.api.domain.repository;

import br.com.rotafood.api.domain.entity.command.Command;
import br.com.rotafood.api.domain.entity.command.CommandStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommandRepository extends JpaRepository<Command, UUID> {
    
    List<Command> findAllByMerchantIdAndStatus(UUID merchantId, CommandStatus status);

    Optional<Command> findByIdAndMerchantId(UUID id, UUID merchantId);

    @Query("""
        SELECT MAX(c.merchantSequence) 
          FROM Command c 
         WHERE c.merchant.id = :merchantId
    """)
    Long findMaxMerchantSequenceByMerchantId(UUID merchantId);

}
