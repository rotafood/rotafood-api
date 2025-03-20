package br.com.rotafood.api.application.service.catalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rotafood.api.application.dto.catalog.ProductOptionGroupDto;
import br.com.rotafood.api.domain.entity.catalog.OptionGroup;
import br.com.rotafood.api.domain.entity.catalog.Product;
import br.com.rotafood.api.domain.entity.catalog.ProductOptionGroup;
import br.com.rotafood.api.domain.repository.ProductOptionGroupRepository;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ProductOptionGroupService {

    @Autowired
    private ProductOptionGroupRepository productOptionGroupRepository;

    @Autowired
    private OptionGroupService optionGroupService;


    @Transactional
    public List<ProductOptionGroup> createOrUpdateAll(List<ProductOptionGroupDto> productOptionGroupDtos, Product product, UUID merchantId) {
        if (productOptionGroupDtos == null || productOptionGroupDtos.isEmpty()) {
            List<ProductOptionGroup> toRemove = product.getProductOptionGroups();
            product.getProductOptionGroups().clear();
            productOptionGroupRepository.deleteAll(toRemove);
            return List.of();
        }

        List<UUID> incomingIds = productOptionGroupDtos.stream()
                .map(ProductOptionGroupDto::id)
                .filter(Objects::nonNull)
                .toList();

        product.getProductOptionGroups().removeIf(pog -> !incomingIds.contains(pog.getId()));

        List<ProductOptionGroup> updatedProductOptionGroups = productOptionGroupDtos.stream()
                .map(dto -> createOrUpdate(dto, product, merchantId))
                .toList();

        return updatedProductOptionGroups;
    }

    private ProductOptionGroup createOrUpdate(ProductOptionGroupDto dto, Product product, UUID merchantId) {
        ProductOptionGroup productOptionGroup = product.getProductOptionGroups().stream()
                .filter(pog -> pog.getId() != null && pog.getId().equals(dto.id()))
                .findFirst()
                .orElseGet(() -> {
                    ProductOptionGroup newPog = new ProductOptionGroup();
                    newPog.setProduct(product);
                    product.getProductOptionGroups().add(newPog);
                    return newPog;
                });

        OptionGroup optionGroup = optionGroupService.updateOrCreate(dto.optionGroup(), merchantId);

        productOptionGroup.setOptionGroup(optionGroup);

        productOptionGroup.setIndex(dto.index());

        productOptionGroup.setMin(dto.min());
        
        productOptionGroup.setMax(dto.max());

        return productOptionGroup;
    }
}
