package br.com.rotafood.api.merchant.domain.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotafood.api.catalog.domain.entity.SellingOption;


public interface SellingOptionRepository extends JpaRepository<SellingOption, UUID>  {



}