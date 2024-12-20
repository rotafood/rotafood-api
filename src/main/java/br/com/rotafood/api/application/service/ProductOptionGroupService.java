package br.com.rotafood.api.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rotafood.api.application.dto.catalog.ProductOptionGroupDto;
import br.com.rotafood.api.domain.entity.catalog.ProductOptionGroup;
import br.com.rotafood.api.domain.entity.catalog.OptionGroup;
import br.com.rotafood.api.domain.repository.ProductOptionGroupRepository;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProductOptionGroupService {

    @Autowired
    private ProductOptionGroupRepository productOptionGroupRepository;

    @Autowired
    private OptionGroupService optionGroupService;


    @Transactional
    public List<ProductOptionGroup> createOrUpdateAll(List<ProductOptionGroupDto> productOptionGroupDtos, UUID productId, UUID merchantId) {
        List<ProductOptionGroup> existingProductOptionGroups = productOptionGroupRepository.findAllByProductId(productId);

        List<UUID> incomingIds = productOptionGroupDtos.stream()
            .map(ProductOptionGroupDto::id)
            .filter(id -> id != null)
            .toList();

        List<ProductOptionGroup> toRemove = existingProductOptionGroups.stream()
            .filter(pog -> !incomingIds.contains(pog.getId()))
            .toList();

        if (!toRemove.isEmpty()) {
            productOptionGroupRepository.deleteAll(toRemove);
        }

        return productOptionGroupDtos.stream().map(productOptionGroupDto -> {
            OptionGroup optionGroup = optionGroupService.updateOrCreate(productOptionGroupDto.optionGroup(), merchantId);

            ProductOptionGroup productOptionGroup = productOptionGroupDto.id() != null
                ? existingProductOptionGroups.stream()
                    .filter(pog -> pog.getId().equals(productOptionGroupDto.id()))
                    .findFirst()
                    .orElse(new ProductOptionGroup())
                : new ProductOptionGroup();

            productOptionGroup.setOptionGroup(optionGroup);
            productOptionGroup.setIndex(productOptionGroupDto.index());
            productOptionGroup.setMin(productOptionGroupDto.min());
            productOptionGroup.setMax(productOptionGroupDto.max());

            return productOptionGroupRepository.save(productOptionGroup);
        }).toList();
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
