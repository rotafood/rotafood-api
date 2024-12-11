package br.com.rotafood.api.application.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rotafood.api.application.dto.catalog.OptionDto;
import br.com.rotafood.api.domain.entity.catalog.Option;
import br.com.rotafood.api.domain.entity.catalog.OptionGroup;
import br.com.rotafood.api.domain.repository.OptionRepository;
import jakarta.transaction.Transactional;

@Service
public class OptionService {

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private PriceService priceService;

    @Transactional
    public Option updateOrCreate(OptionDto optionDto, OptionGroup optionGroup) {
        Option option = optionDto.id() != null
            ? optionRepository.findByIdAndOptionGroupId(optionDto.id(), optionGroup.getId()) : new Option();

        option.setStatus(optionDto.status());
        option.setIndex(optionDto.index());
        option.setOptionGroup(optionGroup);

        if (optionDto.price() != null) {
            option.setPrice(priceService.updateOrCreate(optionDto.price()));
        }

        return optionRepository.save(option);
    }
}
