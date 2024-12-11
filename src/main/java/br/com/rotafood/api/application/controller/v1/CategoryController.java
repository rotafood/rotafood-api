package br.com.rotafood.api.application.controller.v1;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rotafood.api.application.dto.catalog.CategoryDto;
import br.com.rotafood.api.application.dto.catalog.GetCategoryDto;
import br.com.rotafood.api.application.service.CategoryService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/merchants/{merchantId}/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PreAuthorize("hasAuthority('CATEGORY')")
    @GetMapping
    public List<GetCategoryDto> getAll(
        @PathVariable UUID merchantId
    ) {
        var categories = categoryService.getAllByMerchantId(merchantId).stream()
            .map(GetCategoryDto::new)
            .toList();
        return categories;
    }

    @PreAuthorize("hasAuthority('CATEGORY')")
    @GetMapping("simplified")
    public List<CategoryDto> getAllSimplied(
        @PathVariable UUID merchantId
    ) {
        var categories = categoryService.getAllByMerchantId(merchantId).stream()
            .map(CategoryDto::new)
            .toList();
        return categories;
    }

    @PreAuthorize("hasAuthority('CATEGORY')")
    @GetMapping("/{categoryId}")
    public GetCategoryDto getById(
        @PathVariable UUID merchantId,
        @PathVariable UUID categoryId
    ) {
        return new GetCategoryDto(
            categoryService.getByIdAndMerchantId(categoryId, merchantId)
            );
    }

    @PreAuthorize("hasAuthority('CATEGORY')")
    @PutMapping
    public CategoryDto updateOrCreate(
        @PathVariable UUID merchantId,
        @RequestBody @Valid CategoryDto categoryDto
    ) {
        var category = categoryService.updateOrCreate(categoryDto, merchantId);
        return new CategoryDto(category);
    }

}
