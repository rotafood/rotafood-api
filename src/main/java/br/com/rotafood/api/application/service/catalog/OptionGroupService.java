package br.com.rotafood.api.application.service.catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rotafood.api.application.dto.catalog.OptionDto;
import br.com.rotafood.api.application.dto.catalog.OptionGroupDto;
import br.com.rotafood.api.domain.entity.catalog.ProductOptionGroup;
import br.com.rotafood.api.domain.entity.catalog.Option;
import br.com.rotafood.api.domain.entity.catalog.OptionGroup;
import br.com.rotafood.api.domain.entity.catalog.OptionGroupType;
import br.com.rotafood.api.domain.entity.merchant.Merchant;
import br.com.rotafood.api.domain.repository.ProductOptionGroupRepository;
import br.com.rotafood.api.domain.repository.ProductRepository;
import br.com.rotafood.api.domain.repository.MerchantRepository;
import br.com.rotafood.api.domain.repository.OptionGroupRepository;
import jakarta.transaction.Transactional;

@Service
public class OptionGroupService {

    @Autowired
    private OptionGroupRepository optionGroupRepository;

    @Autowired
    private ProductOptionGroupRepository productOptionGroupRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private OptionService optionService;

    @Transactional
    public OptionGroup getByIdAndMerchantId(UUID optionGroupId, UUID merchantId) {
        return optionGroupRepository.findById(optionGroupId)
            .filter(optionGroup -> optionGroup.getMerchant().getId().equals(merchantId))
            .orElseThrow(() -> new IllegalArgumentException("OptionGroup não encontrado ou não pertence ao Merchant."));
    }

    @Transactional
    public OptionGroup updateOrCreate(OptionGroupDto optionGroupDto, UUID merchantId) {
        Merchant merchant = merchantRepository.findById(merchantId)
            .orElseThrow(() -> new IllegalArgumentException("Merchant não encontrado."));
    
        OptionGroup optionGroup = optionGroupDto.id() != null
            ? optionGroupRepository.findById(optionGroupDto.id())
                .orElseThrow(() -> new IllegalArgumentException("OptionGroup não encontrado."))
            : new OptionGroup();
    
        optionGroup.setName(optionGroupDto.name());
        optionGroup.setStatus(optionGroupDto.status());
        optionGroup.setOptionGroupType(optionGroupDto.optionGroupType());
        optionGroup.setMerchant(merchant);

    
        optionGroupRepository.save(optionGroup);
    
        if (optionGroupDto.options() == null || optionGroupDto.options().isEmpty()) {
            List<Option> toRemove = new ArrayList<>(optionGroup.getOptions());
            optionGroup.getOptions().clear();
            optionService.deleteAll(toRemove);
            return optionGroupRepository.save(optionGroup);
        }
    
        List<UUID> incomingOptionIds = optionGroupDto.options().stream()
            .map(OptionDto::id)
            .filter(Objects::nonNull)
            .toList();
    
        List<Option> optionsToRemove = optionGroup.getOptions().stream()
            .filter(existingOption -> !incomingOptionIds.contains(existingOption.getId()))
            .toList();
    
        optionsToRemove.forEach(optionService::unlinkAndDeleteOption);

    
        List<Option> updatedOptions = optionGroupDto.options().stream()
            .map(optionDto -> optionService.updateOrCreate(optionDto, optionGroup))
            .toList();
    
        optionGroup.getOptions().clear();
        optionGroup.getOptions().addAll(updatedOptions);
    
        return optionGroupRepository.save(optionGroup);
    }
    

    @Transactional
    public void linkOptionGroupToProduct(UUID productId, UUID optionGroupId, Integer index, UUID merchantId) {
        var product = productRepository.findByIdAndMerchantId(productId, merchantId);
        var optionGroup = optionGroupRepository.getReferenceById(optionGroupId);

        var productOptionGroup = new ProductOptionGroup();
        productOptionGroup.setProduct(product);
        productOptionGroup.setOptionGroup(optionGroup);
        productOptionGroup.setIndex(index);

        productOptionGroupRepository.save(productOptionGroup);
    }



    @Transactional
    public void unlinkOptionGroupFromItem(UUID productId, UUID optionGroupId, UUID merchantId) {
        var product = productRepository.findByIdAndMerchantId(productId, merchantId);
        var productOptionGroup = productOptionGroupRepository.findByIdAndOptionGroupId(product.getId(), optionGroupId);

        productOptionGroupRepository.delete(productOptionGroup);
    }

    @Transactional
    public void deleteByIdAndMerchantId(UUID optionGroupId, UUID merchantId) {
        var optionGroup = optionGroupRepository.findById(optionGroupId)
            .orElseThrow(() -> new IllegalArgumentException("OptionGroup não encontrado."));

        if (!optionGroup.getMerchant().getId().equals(merchantId)) {
            throw new IllegalArgumentException("OptionGroup não pertence ao Merchant informado.");
        }

        optionGroupRepository.delete(optionGroup);
    }


    public List<OptionGroup> getAllByMerchantId(UUID merchantId){
        return this.optionGroupRepository.findAllByMerchantId(merchantId);
    }

    public List<OptionGroup> getAllByMerchantIdAndOptionGroupType(UUID merchantId, OptionGroupType optionGroupType) {
        return this.optionGroupRepository.findAllByMerchantIdAndOptionGroupType(merchantId, optionGroupType);
    }
    
}
