package br.com.rotafood.api.application.service;


import br.com.rotafood.api.application.dto.order.OrderPaymentMethodDto;
import br.com.rotafood.api.domain.entity.order.OrderPayment;
import br.com.rotafood.api.domain.entity.order.OrderPaymentMethod;
import br.com.rotafood.api.domain.repository.OrderPaymentMethodRepository;
import br.com.rotafood.api.domain.repository.OrderPaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderPaymentMethodService {

    @Autowired
    private OrderPaymentMethodRepository orderPaymentMethodRepository;

    @Autowired
    private OrderPaymentRepository orderPaymentRepository;

    @Transactional
    public OrderPaymentMethod createOrUpdate(OrderPaymentMethodDto dto, UUID paymentId) {
        OrderPayment payment = orderPaymentRepository.findById(paymentId)
                .orElseThrow(() -> new EntityNotFoundException("OrderPayment not found."));

        OrderPaymentMethod method = dto.id() != null
                ? orderPaymentMethodRepository.findById(dto.id())
                        .orElseThrow(() -> new EntityNotFoundException("OrderPaymentMethod not found."))
                : new OrderPaymentMethod();

        method.setDescription(dto.description());
        method.setMethod(dto.method());
        method.setValue(dto.value());
        method.setPayment(payment);

        return orderPaymentMethodRepository.save(method);
    }

    @Transactional
    public void deleteById(UUID methodId) {
        OrderPaymentMethod method = orderPaymentMethodRepository.findById(methodId)
                .orElseThrow(() -> new EntityNotFoundException("OrderPaymentMethod not found."));
        orderPaymentMethodRepository.delete(method);
    }

    public OrderPaymentMethod getById(UUID methodId) {
        return orderPaymentMethodRepository.findById(methodId)
                .orElseThrow(() -> new EntityNotFoundException("OrderPaymentMethod not found."));
    }
}
