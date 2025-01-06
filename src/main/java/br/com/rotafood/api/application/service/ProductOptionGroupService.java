package br.com.rotafood.api.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rotafood.api.application.dto.catalog.ProductOptionGroupDto;
import br.com.rotafood.api.domain.entity.catalog.ProductOptionGroup;
import br.com.rotafood.api.domain.entity.catalog.OptionGroup;
import br.com.rotafood.api.domain.entity.catalog.Product;
import br.com.rotafood.api.domain.repository.ProductOptionGroupRepository;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
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
            List<ProductOptionGroup> toRemove = new ArrayList<>(product.getProductOptionGroups());
            product.getProductOptionGroups().clear();
            deleteAll(toRemove);
            return List.of();
        }

        List<UUID> incomingIds = productOptionGroupDtos.stream()
            .map(ProductOptionGroupDto::id)
            .filter(Objects::nonNull)
            .toList();

        List<ProductOptionGroup> toRemove = product.getProductOptionGroups().stream()
            .filter(pog -> !incomingIds.contains(pog.getId()))
            .toList();

        product.getProductOptionGroups().removeAll(toRemove);
        this.deleteAll(toRemove);

        List<ProductOptionGroup> updatedProductOptionGroups = productOptionGroupDtos.stream().map(productOptionGroupDto -> {
            OptionGroup optionGroup = optionGroupService.updateOrCreate(productOptionGroupDto.optionGroup(), merchantId);

            ProductOptionGroup productOptionGroup = productOptionGroupDto.id() != null
                ? productOptionGroupRepository.findById(productOptionGroupDto.id())
                    .orElse(new ProductOptionGroup())
                : new ProductOptionGroup();

            productOptionGroup.setOptionGroup(optionGroup);
            productOptionGroup.setProduct(product);
            productOptionGroup.setIndex(productOptionGroupDto.index());
            productOptionGroup.setMin(productOptionGroupDto.min());
            productOptionGroup.setMax(productOptionGroupDto.max());

            return productOptionGroupRepository.save(productOptionGroup);
        }).toList();

        updatedProductOptionGroups.forEach(updated -> {
            if (!product.getProductOptionGroups().contains(updated)) {
                product.getProductOptionGroups().add(updated);
            }
        });

        return updatedProductOptionGroups;
    }


    @Transactional
    public void unlinkOptionGroup(UUID productOptionGroupId) {
        productOptionGroupRepository.deleteById(productOptionGroupId);
    }

    public List<ProductOptionGroup> getAllByProductId(UUID productId) {
        return productOptionGroupRepository.findAllByProductId(productId);
    }

    public void deleteAll(List<ProductOptionGroup> productOptionGroups) {
        productOptionGroupRepository.deleteAll(productOptionGroups);
    }
}
