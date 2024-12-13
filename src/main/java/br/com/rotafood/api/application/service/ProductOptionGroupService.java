package br.com.rotafood.api.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rotafood.api.application.dto.catalog.ProductOptionGroupDto;
import br.com.rotafood.api.domain.entity.catalog.ProductOptionGroup;
import br.com.rotafood.api.domain.entity.catalog.OptionGroup;
import br.com.rotafood.api.domain.entity.catalog.Product;
import br.com.rotafood.api.domain.repository.ProductOptionGroupRepository;
import br.com.rotafood.api.domain.repository.ProductRepository;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProductOptionGroupService {

    @Autowired
    private ProductOptionGroupRepository productOptionGroupRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OptionGroupService optionGroupService;


    @Transactional
    public List<ProductOptionGroup> createOrUpdate(UUID productId, List<ProductOptionGroupDto> productOptionGroupDtos, UUID merchantId) {
        Product product = productRepository.getReferenceById(productId);

        if (product != null && product.getProductOptionGroups() != null && !product.getProductOptionGroups().isEmpty()) {
            this.productOptionGroupRepository.deleteAll(product.getProductOptionGroups());
        }

        return productOptionGroupDtos.stream().map(productOptionGroupDto -> {
            OptionGroup optionGroup = optionGroupService.updateOrCreate(productOptionGroupDto.optionGroup(), merchantId);
            
            var productOptionGroup = new ProductOptionGroup();
            productOptionGroup.setProduct(product);
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
}
