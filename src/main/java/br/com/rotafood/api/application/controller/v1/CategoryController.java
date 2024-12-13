package br.com.rotafood.api.application.controller.v1;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rotafood.api.application.dto.catalog.CategoryDto;
import br.com.rotafood.api.application.dto.catalog.FullCategoryDto;
import br.com.rotafood.api.application.service.CategoryService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/merchants/{merchantId}/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PreAuthorize("hasAuthority('CATEGORY')")
    @GetMapping
    public List<FullCategoryDto> getAll(
        @PathVariable UUID merchantId
    ) {
        return  categoryService.getAllByMerchantId(merchantId).stream()
            .map(FullCategoryDto::new)
            .toList();
    }

    @PreAuthorize("hasAuthority('CATEGORY')")
    @GetMapping("simplified")
    public List<CategoryDto> getAllSimplied(
        @PathVariable UUID merchantId
    ) {
        return categoryService.getAllByMerchantId(merchantId).stream()
            .map(CategoryDto::new)
            .toList();
    }

    @PreAuthorize("hasAuthority('CATEGORY')")
    @GetMapping("/{categoryId}")
    public FullCategoryDto getById(
        @PathVariable UUID merchantId,
        @PathVariable UUID categoryId
    ) {
        return new FullCategoryDto(
            categoryService.getByIdAndMerchantId(categoryId, merchantId)
            );
    }

    @PreAuthorize("hasAuthority('CATEGORY')")
    @DeleteMapping("/{categoryId}")
    public void deleteById(
        @PathVariable UUID merchantId,
        @PathVariable UUID categoryId
    ) {
        categoryService.deleteByIdAndMerchantId(categoryId, merchantId);
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
