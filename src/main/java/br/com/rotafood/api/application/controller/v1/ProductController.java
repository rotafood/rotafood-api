package br.com.rotafood.api.application.controller.v1;

import br.com.rotafood.api.application.dto.catalog.ProductDto;
import br.com.rotafood.api.application.mappers.ProductMapper;
import br.com.rotafood.api.application.service.ProductService;
import br.com.rotafood.api.domain.entity.catalog.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ApiVersion.VERSION + "/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts().stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable UUID id) {
        Product product = productService.getProductById(id);
        ProductDto productDto = ProductMapper.toDto(product);
        return ResponseEntity.ok(productDto);
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        ProductDto productDto = ProductMapper.toDto(createdProduct);
        return ResponseEntity.ok(productDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable UUID id,
            @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        ProductDto productDto = ProductMapper.toDto(updatedProduct);
        return ResponseEntity.ok(productDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
