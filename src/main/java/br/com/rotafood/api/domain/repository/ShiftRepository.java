package br.com.rotafood.api.domain.repository;


import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotafood.api.domain.entity.catalog.Shift;

public interface ShiftRepository extends JpaRepository<Shift, UUID> {

    List<Shift> findByItemId(UUID itemId);

    List<Shift> findByMerchantId(UUID merchantId);

    void deleteByItemId(UUID itemId);    
  
    void deleteByMerchantId(UUID merchantId);     


       
}
