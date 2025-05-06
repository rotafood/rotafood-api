package br.com.rotafood.api.catalog.application.controller.v1;

import br.com.rotafood.api.catalog.application.dto.ProductDto;
import br.com.rotafood.api.catalog.application.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ApiVersion.VERSION + "/merchants/{merchantId}/products")
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(@PathVariable UUID merchantId) {
        List<ProductDto> products = productService.getAll(merchantId)
                .stream()
                .filter(product -> !"Pizza".equalsIgnoreCase(product.getName()))
                .map(ProductDto::new)
                .toList();
        return ResponseEntity.ok(products);
    }

}
