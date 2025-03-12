package br.com.rotafood.api.application.dto.merchant;


import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.application.dto.address.AddressDto;
import br.com.rotafood.api.application.dto.catalog.ShiftDto;
import br.com.rotafood.api.domain.entity.merchant.DocumentType;
import br.com.rotafood.api.domain.entity.merchant.Merchant;
import br.com.rotafood.api.domain.entity.merchant.MerchantType;

public record FullMerchantDto (
    UUID id,
    String name,
    String corporateName,
    String onlineName,
    String imagePath,
    String description,
    DocumentType documentType,
    String document,
    String phone,
    MerchantType merchantType,
    Date createdAt,
    Instant lastOpenedUtc,
    AddressDto address,
    List<ShiftDto> openingHours
    ) {

    public FullMerchantDto(Merchant merchant) {
       this(merchant.getId(), 
       merchant.getName(), 
       merchant.getCorporateName(), 
       merchant.getOnlineName(),
       merchant.getImagePath(),
       merchant.getDescription(), 
       merchant.getDocumentType(), 
       merchant.getDocument(), 
       merchant.getPhone(),
       merchant.getMerchantType(), 
       merchant.getCreatedAt(), 
       merchant.getLastOpenedUtc(),
       new AddressDto(merchant.getAddress()),
       merchant.getOpeningHours().stream().map(ShiftDto::new).toList()
       );
    }
}
