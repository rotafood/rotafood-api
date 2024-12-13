package br.com.rotafood.api.application.controller.v1;

import br.com.rotafood.api.application.dto.catalog.OptionGroupDto;
import br.com.rotafood.api.application.service.OptionGroupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/merchants/{merchantId}/optionGroups")
public class OptionGroupController {

    @Autowired
    private OptionGroupService optionGroupService;

    @GetMapping
    public List<OptionGroupDto> getAll(@PathVariable UUID merchantId) {
        return optionGroupService.getAllByMerchantId(merchantId)
                .stream()
                .map(OptionGroupDto::new)
                .toList();
    }

    @GetMapping("/{optionGroupId}")
    public OptionGroupDto getById(@PathVariable UUID merchantId, @PathVariable UUID optionGroupId) {
        return new OptionGroupDto(optionGroupService.getByIdAndMerchantId(optionGroupId, merchantId));
    }

    @PutMapping
    public OptionGroupDto updateOrCreate(
            @PathVariable UUID merchantId,
            @RequestBody @Valid OptionGroupDto optionGroupDto) {
        return new OptionGroupDto(optionGroupService.updateOrCreate(optionGroupDto, merchantId));
    }

    @DeleteMapping("/{optionGroupId}")
    public void delete(@PathVariable UUID merchantId, @PathVariable UUID optionGroupId) {
        optionGroupService.deleteByIdAndMerchantId(optionGroupId, merchantId);
    }
}
