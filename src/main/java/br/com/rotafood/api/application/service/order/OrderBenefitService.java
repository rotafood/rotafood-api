package br.com.rotafood.api.application.service.order;

import br.com.rotafood.api.application.dto.order.OrderBenefitDto;
import br.com.rotafood.api.domain.entity.order.Order;
import br.com.rotafood.api.domain.entity.order.OrderBenefit;
import br.com.rotafood.api.domain.repository.OrderBenefitRepository;
import br.com.rotafood.api.domain.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderBenefitService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderBenefitRepository orderBenefitRepository;

    @Transactional
    public OrderBenefit createOrUpdate(OrderBenefitDto dto, UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));

        OrderBenefit benefit = dto.id() != null
                ? orderBenefitRepository.findById(dto.id())
                        .orElseThrow(() -> new EntityNotFoundException("OrderBenefit not found."))
                : new OrderBenefit();

        benefit.setValue(dto.value());
        benefit.setTarget(dto.target());

        order.getBenefits().add(benefit);

        orderRepository.save(order);
        return orderBenefitRepository.save(benefit);
    }
}
