package br.com.rotafood.api.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rotafood.api.application.dto.catalog.ItemOptionGroupDto;
import br.com.rotafood.api.domain.entity.catalog.Item;
import br.com.rotafood.api.domain.entity.catalog.ItemOptionGroup;
import br.com.rotafood.api.domain.entity.catalog.OptionGroup;
import br.com.rotafood.api.domain.repository.ItemOptionGroupRepository;
import br.com.rotafood.api.domain.repository.ItemRepository;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ItemOptionGroupService {

    @Autowired
    private ItemOptionGroupRepository itemOptionGroupRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OptionGroupService optionGroupService;

    /**
     * Atualiza ou cria todos os ItemOptionGroups de um item. Remove os existentes antes de recriar com base na request.
     */
    @Transactional
    public List<ItemOptionGroup> createOrUpdate(UUID itemId, List<ItemOptionGroupDto> itemOptionGroupDtos, UUID merchantId) {
        // Busca o item
        Item item = itemRepository.getReferenceById(itemId);

        if (item != null && item.getItemOptionGroups() != null && !item.getItemOptionGroups().isEmpty()) {
            // Remove todos os links existentes
            this.itemOptionGroupRepository.deleteAll(item.getItemOptionGroups());
        }

        // Recria os links com base nos dados recebidos
        List<ItemOptionGroup> newItemOptionGroups = itemOptionGroupDtos.stream().map(itemOptionGroupDto -> {
            OptionGroup optionGroup = optionGroupService.updateOrCreate(itemOptionGroupDto.optionGroup(), merchantId);

            ItemOptionGroup itemOptionGroup = new ItemOptionGroup();
            itemOptionGroup.setItem(item);
            itemOptionGroup.setOptionGroup(optionGroup);
            itemOptionGroup.setIndex(itemOptionGroupDto.index());
            itemOptionGroup.setMin(itemOptionGroupDto.min());
            itemOptionGroup.setMax(itemOptionGroupDto.max());

            return itemOptionGroupRepository.save(itemOptionGroup);
        }).toList();

        return newItemOptionGroups;
    }

    @Transactional
    public void unlinkOptionGroup(UUID itemOptionGroupId) {
        itemOptionGroupRepository.deleteById(itemOptionGroupId);
    }

    public List<ItemOptionGroup> getAllByItemId(UUID itemId) {
        return itemOptionGroupRepository.findAllByItemId(itemId);
    }
}
