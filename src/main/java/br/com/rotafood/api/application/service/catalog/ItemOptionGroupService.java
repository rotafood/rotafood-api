package br.com.rotafood.api.application.service.catalog;

import br.com.rotafood.api.application.dto.catalog.FullItemOptionGroupDto;
import br.com.rotafood.api.domain.entity.catalog.Item;
import br.com.rotafood.api.domain.entity.catalog.ItemOptionGroup;
import br.com.rotafood.api.domain.repository.ItemOptionGroupRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ItemOptionGroupService {

    @Autowired
    private ItemOptionGroupRepository itemOptionGroupRepository;

    @Autowired
    private OptionGroupService optionGroupService;

    @Transactional
    public List<ItemOptionGroup> createOrUpdateAll(List<FullItemOptionGroupDto> dtos, Item item, UUID merchantId, boolean updateOptionGroups) {
        if (dtos == null || dtos.isEmpty()) {
            new ArrayList<>(item.getItemOptionGroups()).forEach(item::removeItemOptionGroup);
            return List.of();
        }

        List<UUID> incomingIds = dtos.stream()
                .map(FullItemOptionGroupDto::id)
                .filter(Objects::nonNull)
                .toList();

        List<ItemOptionGroup> toRemove = item.getItemOptionGroups().stream()
                .filter(iog -> iog.getId() != null && !incomingIds.contains(iog.getId()))
                .toList();

        toRemove.forEach(item::removeItemOptionGroup);

        dtos.forEach(dto -> {
            ItemOptionGroup group = dto.id() != null ? 
                itemOptionGroupRepository.findById(dto.id()).orElseThrow(() -> new EntityNotFoundException("Grupo não encontrado: " + dto.id()))
                : new ItemOptionGroup();

            if (!item.getItemOptionGroups().contains(group)) {
                item.addItemOptionGroup(group);
            }
            if (updateOptionGroups) {
                group.setOptionGroup(optionGroupService.updateOrCreate(dto.optionGroup(), merchantId));
            }
            if (!updateOptionGroups) {
                group.setOptionGroup(optionGroupService.getByIdAndMerchantId(dto.optionGroup().id(), merchantId));
            }
            group.setIndex(dto.index());
            group.setMin(dto.min());
            group.setMax(dto.max());
        });

        return item.getItemOptionGroups();
    }

}
