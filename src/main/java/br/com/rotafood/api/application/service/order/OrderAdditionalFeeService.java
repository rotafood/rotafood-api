package br.com.rotafood.api.application.service.order;

import br.com.rotafood.api.application.dto.order.OrderAdditionalFeeDto;
import br.com.rotafood.api.domain.entity.order.Order;
import br.com.rotafood.api.domain.entity.order.OrderAdditionalFee;
import br.com.rotafood.api.domain.repository.OrderAdditionalFeeRepository;
import br.com.rotafood.api.domain.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderAdditionalFeeService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderAdditionalFeeRepository orderAdditionalFeeRepository;

    @Transactional
    public OrderAdditionalFee createOrUpdate(OrderAdditionalFeeDto dto, UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));

        OrderAdditionalFee additionalFee = dto.id() != null
                ? orderAdditionalFeeRepository.findById(dto.id())
                        .orElseThrow(() -> new EntityNotFoundException("OrderAdditionalFee not found."))
                : new OrderAdditionalFee();

        additionalFee.setName(dto.name());
        additionalFee.setType(dto.type());
        additionalFee.setValue(dto.value());
        additionalFee.setDescription(dto.description());

        order.getAdditionalFees().add(additionalFee);

        orderRepository.save(order);
        return orderAdditionalFeeRepository.save(additionalFee);
    }
}
