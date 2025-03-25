package br.com.rotafood.api.application.service.order;

import br.com.rotafood.api.application.dto.order.OrderBenefitDto;
import br.com.rotafood.api.domain.entity.order.Order;
import br.com.rotafood.api.domain.entity.order.OrderBenefit;
import br.com.rotafood.api.domain.repository.OrderBenefitRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class OrderBenefitService {

    @Autowired
    private OrderBenefitRepository orderBenefitRepository;

    @Transactional
    public OrderBenefit createOrUpdate(OrderBenefitDto benefitDto, Order order) {
        OrderBenefit benefit = benefitDto.id() != null
                ? orderBenefitRepository.findById(benefitDto.id())
                        .orElseThrow(() -> new EntityNotFoundException("Benefício não encontrado."))
                : new OrderBenefit();

        benefit.setValue(benefitDto.value());
        benefit.setDescription(benefitDto.description());
        benefit.setTarget(benefitDto.target());

        order.getBenefits().add(benefit);

        return orderBenefitRepository.save(benefit);
    }

    @Transactional
    public void synchronizeBenefits(List<OrderBenefitDto> benefitDtos, Order order) {
        List<UUID> newBenefitIds = benefitDtos.stream()
                .map(OrderBenefitDto::id)
                .filter(Objects::nonNull)
                .toList();

        order.getBenefits().removeIf(existingBenefit ->
                existingBenefit.getId() != null && !newBenefitIds.contains(existingBenefit.getId()));

        benefitDtos.forEach(benefitDto -> this.createOrUpdate(benefitDto, order));
    }
}
