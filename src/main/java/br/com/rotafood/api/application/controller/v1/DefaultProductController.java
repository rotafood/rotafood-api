package br.com.rotafood.api.application.controller.v1;

import br.com.rotafood.api.application.dto.catalog.DefaultProductDto;
import br.com.rotafood.api.domain.entity.catalog.DefaultProduct;
import br.com.rotafood.api.domain.repository.DefaultProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/default-products")
public class DefaultProductController {

    @Autowired
    private DefaultProductRepository defaultProductRepository;

    @GetMapping
    public List<DefaultProductDto> getAllDefaultProducts(@RequestParam(required = false) String search) {
        if (search == null || search.isBlank()) {
            return List.of();
        }

        return defaultProductRepository.findByNameContaining(
            search, 
            PageRequest.of(0, 30)
        ).stream().map(DefaultProductDto::new).toList();
    }
}
