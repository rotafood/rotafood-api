package br.com.rotafood.api.application.dto.merchant;

import java.util.Date;
import java.util.UUID;

import br.com.rotafood.api.application.dto.address.AddressDto;
import br.com.rotafood.api.domain.entity.merchant.DocumentType;
import br.com.rotafood.api.domain.entity.merchant.Merchant;
import br.com.rotafood.api.domain.entity.merchant.MerchantType;

public record MerchantDto (
    UUID id,
    String name,
    String corporateName,
    String onlineName,
    String imagePath,
    String description,
    DocumentType documentType,
    String document,
    MerchantType merchantType,
    Date createdAt,
    AddressDto address
    ) {

    public MerchantDto(Merchant merchant) {
       this(merchant.getId(), 
       merchant.getName(), 
       merchant.getCorporateName(), 
       merchant.getOnlineName(),
       merchant.getImagePath(),
       merchant.getDescription(), 
       merchant.getDocumentType(), 
       merchant.getDocument(), 
       merchant.getMerchantType(), 
       merchant.getCreatedAt(), 
       new AddressDto(merchant.getAddress()));
    }
}
