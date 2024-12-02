package br.com.rotafood.api.application.controller.v1;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rotafood.api.application.dto.catalog.CategoryDto;
import br.com.rotafood.api.application.dto.catalog.GetCategoryDto;
import br.com.rotafood.api.application.service.CategoryService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/merchants/{merchantId}/catalogs/{catalogId}/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<GetCategoryDto> getAll(
        @PathVariable UUID merchantId,
        @PathVariable UUID catalogId
    ) {
        return 
        categoryService
            .getAllByCatalogIdAndMerchantId(catalogId, merchantId)
                .stream()
                    .map(GetCategoryDto::new).toList();
    }

    @GetMapping("/{categoryId}")
    public GetCategoryDto getById(
        @PathVariable UUID merchantId,
        @PathVariable UUID catalogId,
        @PathVariable UUID categoryId
    ) {
        return new GetCategoryDto(
            categoryService.getByIdAndCatalogIdAndMerchantId(categoryId, catalogId, merchantId)
            );
    }

    @PostMapping
    public CategoryDto create(
        @PathVariable UUID merchantId,
        @PathVariable UUID catalogId,
        @RequestBody @Valid CategoryDto categoryDto
    ) {
        return new CategoryDto(categoryService.create(categoryDto, catalogId, merchantId));
    }

    @PutMapping("/{categoryId}")
    public CategoryDto update(
        @PathVariable UUID merchantId,
        @PathVariable UUID catalogId,
        @PathVariable UUID categoryId,
        @RequestBody @Valid CategoryDto categoryDto
    ) {
        return new CategoryDto(categoryService.update(categoryDto, categoryId, catalogId, merchantId));
    }
}
