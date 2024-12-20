package br.com.rotafood.api.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rotafood.api.application.dto.catalog.ProductDto;
import br.com.rotafood.api.domain.entity.catalog.DietaryRestrictions;
import br.com.rotafood.api.domain.entity.catalog.Product;
import br.com.rotafood.api.domain.entity.catalog.ProductOptionGroup;
import br.com.rotafood.api.domain.entity.catalog.ProductPackaging;
import br.com.rotafood.api.domain.entity.catalog.Weight;
import br.com.rotafood.api.domain.entity.merchant.Merchant;
import br.com.rotafood.api.domain.repository.MerchantRepository;
import br.com.rotafood.api.domain.repository.ProductRepository;
import br.com.rotafood.api.domain.repository.WeightRepository;

@Service
public class ProductService { 

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WeightRepository weightRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private ProductPackagingService productPackagingService;

    @Autowired
    private ProductOptionGroupService productOptionGroupService;

    @Transactional
    public Product updateOrCreate(ProductDto productDto, UUID merchantId) {
        Merchant merchant = this.merchantRepository.getReferenceById(merchantId);
    
        Product product = productDto.id() != null
                ? productRepository.findById(productDto.id()).orElse(new Product())
                : new Product();
    
        product.setMerchant(merchant);
        product.setName(productDto.name());
        product.setDescription(productDto.description());
        product.setEan(productDto.ean());
        product.setAdditionalInformation(productDto.additionalInformation());
        product.setTags(productDto.tags());
        product.setImagePath(productDto.imagePath());
        product.setServing(productDto.serving());
        product.setPackagingType(productDto.packagingType());
        product.setDietaryRestrictions(productDto.dietaryRestrictions().stream().map(DietaryRestrictions::name).toList());
        product.setQuantity(productDto.quantity());

        productRepository.save(product);


    
        if (productDto.weight() != null) {
            Weight weight = productDto.weight().id() != null 
                ? weightRepository.findById(productDto.weight().id()).orElseGet(Weight::new) 
                : new Weight();
            weight.setQuantity(productDto.weight().quantity());
            weight.setUnit(productDto.weight().unit());
            weight = this.weightRepository.save(weight);

            weight.setProduct(product);
            product.setWeight(weight);
        }
    
    
        if (productDto.packagings() != null) {
            product.getProductPackagings().clear();
    
            List<ProductPackaging> productPackagings = productPackagingService.createOrUpdateAll(
                productDto.packagings(),
                product.getId(),
                merchantId
            );
    
            productPackagings.forEach(cm -> cm.setProduct(product));
            product.getProductPackagings().addAll(productPackagings);
        }
    
        if (productDto.optionGroups() != null) {
            product.getProductOptionGroups().clear();
    
            List<ProductOptionGroup> productOptionGroups = productOptionGroupService.createOrUpdateAll(
                productDto.optionGroups(),
                product.getId(),
                merchantId
            );
    
            productOptionGroups.forEach(cm -> cm.setProduct(product));
            product.getProductOptionGroups().addAll(productOptionGroups);
        }
    
        return product;
    }
    


    @Transactional
    public void deleteById(UUID productId, UUID merchantId) {
        Product product = productRepository.findByIdAndMerchantId(productId, merchantId);
        productRepository.delete(product);
    }

    public Product getById(UUID productId, UUID merchantId) {
        return productRepository.findByIdAndMerchantId(productId, merchantId);
    }

    public List<Product> getAll(UUID merchantId) {
        return productRepository.findAllByMerchantId(merchantId);
    }
}
