package br.com.rotafood.api.application.controller.v1;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import br.com.rotafood.api.application.service.catalog.ProductService;
import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping( ApiVersion.VERSION + "/merchants/{merchantId}/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @DeleteMapping("/{productId}")
    public void delete(
            @PathVariable UUID merchantId,
            @PathVariable @Valid UUID productId) {
        this.productService.deleteById(productId, merchantId);

    }

}
