package br.com.rotafood.api.application.mappers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.rotafood.api.application.dto.catalog.OptionGroupDto;
import br.com.rotafood.api.application.dto.catalog.ProductDto;
import br.com.rotafood.api.application.dto.catalog.ProductSellingOptionDto;
import br.com.rotafood.api.domain.entity.catalog.Item;
import br.com.rotafood.api.domain.entity.catalog.Option;
import br.com.rotafood.api.domain.entity.catalog.Product;
import br.com.rotafood.api.domain.entity.storage.Image;

public class ProductMapper {

    public static ProductDto toDto(Product product) {
        List<OptionGroupDto> optionGroups = product.getOptionGroups().stream()
            .map(optionGroup -> new OptionGroupDto(
                optionGroup.getId(),
                optionGroup.getName(),
                optionGroup.getStatus(),
                optionGroup.getExternalCode(),
                optionGroup.getIndex(),
                optionGroup.getOptionGroupType(),
                optionGroup.getOptions().stream().map(
                    option -> option.getId()
                ).collect(Collectors.toList())
            ))
            .collect(Collectors.toList());

        return new ProductDto(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getEan(),
            product.getAdditionalInformation(),
            product.getProductType(),
            product.getDietaryRestrictions(),
            product.getWeightUnit(),
            product.getWeightQuantity(),
            Optional.ofNullable(product.getItem()).map(Item::getId),
            Optional.ofNullable(product.getOption()).map(Option::getId),
            new ProductSellingOptionDto(),
            Optional.of(optionGroups.stream().map(OptionGroupDto::id).collect(Collectors.toList())),
            product.getMerchant().getId().toString(),
            Optional.ofNullable(product.getImage()).map(Image::getUrl)
        );
    }
}
