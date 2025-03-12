package br.com.rotafood.api.application.controller.v1;

import br.com.rotafood.api.application.dto.catalog.ItemDto;
import br.com.rotafood.api.application.service.catalog.ItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ApiVersion.VERSION + "/merchants/{merchantId}/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    public ResponseEntity<List<ItemDto>> getAllItems(@PathVariable UUID merchantId) {
        List<ItemDto> items = itemService.getAllByMerchantId(merchantId)
                .stream()
                .map(ItemDto::new)
                .toList();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDto> getItemById(@PathVariable UUID merchantId, @PathVariable UUID itemId) {
        ItemDto item = new ItemDto(itemService.getByIdAndMerchantId(itemId, merchantId));
        return ResponseEntity.ok(item);
    }

    @PutMapping
    public ResponseEntity<ItemDto> updateOrCreateItem(
            @PathVariable UUID merchantId,
            @RequestBody @Valid ItemDto itemDto) {
        ItemDto updatedItem = new ItemDto(itemService.updateOrCreate(itemDto, merchantId));
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable UUID merchantId, @PathVariable UUID itemId) {
        itemService.deleteByIdAndMerchantId(itemId, merchantId);
        return ResponseEntity.noContent().build();
    }
}
