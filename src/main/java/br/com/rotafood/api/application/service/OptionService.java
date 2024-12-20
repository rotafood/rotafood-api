package br.com.rotafood.api.application.service;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rotafood.api.application.dto.catalog.OptionDto;
import br.com.rotafood.api.application.dto.catalog.ProductOptionDto;
import br.com.rotafood.api.domain.entity.catalog.ContextModifier;
import br.com.rotafood.api.domain.entity.catalog.Option;
import br.com.rotafood.api.domain.entity.catalog.OptionGroup;
import br.com.rotafood.api.domain.entity.catalog.Product;
import br.com.rotafood.api.domain.entity.catalog.Shift;
import br.com.rotafood.api.domain.entity.merchant.Merchant;
import br.com.rotafood.api.domain.repository.MerchantRepository;
import br.com.rotafood.api.domain.repository.OptionRepository;
import br.com.rotafood.api.domain.repository.ProductRepository;
import jakarta.transaction.Transactional;

@Service
public class OptionService {

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private ContextModifierService contextModifierService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Transactional
    public Option updateOrCreate(OptionDto optionDto, OptionGroup optionGroup) {
        Option option = optionDto.id() != null
            ? optionRepository.findByIdAndOptionGroupId(optionDto.id(), optionGroup.getId())
            : new Option();

        option.setStatus(optionDto.status());
        option.setIndex(optionDto.index());
        option.setOptionGroup(optionGroup);
        
        optionRepository.save(option);

        var product = this.updateOrCreateProductOption(
            optionDto.product(), 
            optionGroup.getMerchant().getId()
        );
        product.setOption(option);
        option.setProduct(product);

        option.getContextModifiers().clear();
        List<ContextModifier> contextModifiers = contextModifierService.updateOrCreateAll(optionDto.contextModifiers());
        option.getContextModifiers().addAll(contextModifiers);
        contextModifiers.forEach(cm -> cm.setOption(option));

        return optionRepository.save(option);
    }

    @Transactional
    public Product updateOrCreateProductOption(ProductOptionDto productDto, UUID merchantId) {

        Merchant merchant = this.merchantRepository.getReferenceById(merchantId);

        Product product = productDto.id() != null
                ? productRepository.findById(productDto.id())
                        .orElse(new Product())
                : new Product();

        product.setMerchant(merchant);
        product.setName(productDto.name());
        product.setDescription(productDto.description());
        product.setEan(productDto.ean());
        product.setImagePath(productDto.imagePath());
        product.setServing(productDto.serving());
        product.setServing(productDto.serving());
        

        return productRepository.save(product);
    }

    @Transactional
    public void unlinkAndDeleteOption(Option option) {
        if (option.getOptionGroup() != null) {
            OptionGroup optionGroup = option.getOptionGroup();
            optionGroup.getOptions().remove(option);
            option.setOptionGroup(null);
        }
    
        optionRepository.delete(option);
    }
    

}
