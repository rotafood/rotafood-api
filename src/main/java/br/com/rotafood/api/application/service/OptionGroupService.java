package br.com.rotafood.api.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rotafood.api.application.dto.catalog.OptionGroupDto;
import br.com.rotafood.api.domain.entity.catalog.ProductOptionGroup;
import br.com.rotafood.api.domain.entity.catalog.OptionGroup;
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
    public List<OptionGroup> getAllByMerchantId(UUID merchantId) {
        return optionGroupRepository.findAllByMerchantId(merchantId);
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
        optionGroup.setMerchant(merchant);
        var options = optionGroupDto.options().stream().map(optionDto -> {
            return this.optionService.updateOrCreate(optionDto, optionGroup);
        }).toList();

        optionGroup.setOptions(options);
        

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
}
