package br.com.rotafood.api.application.controller.v1;

import br.com.rotafood.api.application.dto.catalog.ItemDto;
import br.com.rotafood.api.application.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/merchants/{merchantId}/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    public List<ItemDto> getAllItems(@PathVariable UUID merchantId) {
        return itemService.getAllByMerchantId(merchantId)
                .stream()
                .map(ItemDto::new)
                .toList();
    }

    @GetMapping("/{itemId}")
    public ItemDto getItemById(@PathVariable UUID merchantId, @PathVariable UUID itemId) {
        return new ItemDto(itemService.getByIdAndMerchantId(itemId, merchantId));
    }

    @PutMapping
    public ItemDto updateOrCreateItem(
            @PathVariable UUID merchantId,
            @RequestBody @Valid ItemDto itemDto) {
        return new ItemDto(itemService.updateOrCreate(itemDto, merchantId));
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@PathVariable UUID merchantId, @PathVariable UUID itemId) {
        itemService.deleteByIdAndMerchantId(itemId, merchantId);
    }
}
