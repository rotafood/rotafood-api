package br.com.rotafood.api.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rotafood.api.application.dto.catalog.ProductDto;
import br.com.rotafood.api.domain.entity.catalog.DietaryRestrictions;
import br.com.rotafood.api.domain.entity.catalog.Product;
import br.com.rotafood.api.domain.entity.catalog.SellingOption;
import br.com.rotafood.api.domain.entity.catalog.Weight;
import br.com.rotafood.api.domain.entity.merchant.Merchant;
import br.com.rotafood.api.domain.repository.MerchantRepository;
import br.com.rotafood.api.domain.repository.ProductRepository;
import br.com.rotafood.api.domain.repository.SellingOptionRepository;
import br.com.rotafood.api.domain.repository.WeightRepository;

@Service
public class ProductService { 

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SellingOptionRepository sellingOptionRepository;

    @Autowired
    private WeightRepository weightRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Transactional
    public Product updateOrCreate(ProductDto productDto, UUID merchantId) {
        Merchant merchant = this.merchantRepository.getReferenceById(merchantId);

        Product product = productDto.id() != null
                ? productRepository.findById(productDto.id())
                        .orElse(new Product())
                : new Product();

        product.setName(productDto.name());
        product.setDescription(productDto.description());
        product.setEan(productDto.ean());
        product.setAdditionalInformation(productDto.additionalInformation());
        product.setTags(productDto.tags());
        product.setImagePath(productDto.imagePath());
        product.setMultipleImages(productDto.multipleImages());
        product.setServing(productDto.serving());

        if (productDto.dietaryRestrictions() != null) {
            product.setDietaryRestrictions(productDto.dietaryRestrictions().stream().map(DietaryRestrictions::name).toList());
        }

        SellingOption sellingOption = productDto.sellingOption().id() != null ? sellingOptionRepository.findById(productDto.sellingOption().id())
                                                                                                        .orElse(new SellingOption())
                                                                                                : new SellingOption();
        sellingOption.setAvailableUnits(productDto.sellingOption().availableUnits());
        sellingOption.setIncremental(productDto.sellingOption().incremental());
        sellingOption.setAverageUnit(productDto.sellingOption().averageUnit());
        sellingOption.setMinimum(productDto.sellingOption().minimum());

        sellingOption = sellingOptionRepository.save(sellingOption);

        

        Weight weight = productDto.weight() != null ? weightRepository.findById(productDto.weight().id())
                    .orElseGet(Weight::new) : new Weight();
        weight.setQuantity(productDto.weight().quantity());
        weight.setUnit(productDto.weight().unit());

        weight = this.weightRepository.save(weight);

        product.setSellingOption(sellingOption);
        product.setMerchant(merchant);
        product.setWeight(weightRepository.save(weight));

        return productRepository.save(product);
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
