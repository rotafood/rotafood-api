package br.com.rotafood.api.application.service.order;

import br.com.rotafood.api.application.dto.AddressDto;
import br.com.rotafood.api.application.dto.logistic.RouteDto;
import br.com.rotafood.api.application.dto.order.FullOrderDto;
import br.com.rotafood.api.application.dto.order.OrderItemDto;
import br.com.rotafood.api.application.dto.order.OrderItemOptionDto;
import br.com.rotafood.api.application.dto.order.OrderTotalDto;
import br.com.rotafood.api.application.service.logistic.LogisticService;
import br.com.rotafood.api.domain.entity.catalog.ContextModifier;
import br.com.rotafood.api.domain.entity.merchant.Merchant;
import br.com.rotafood.api.domain.entity.order.OrderTotal;
import br.com.rotafood.api.domain.repository.ContextModifierRepository;
import br.com.rotafood.api.domain.repository.OrderTotalRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderTotalService {

    @Autowired
    private OrderTotalRepository orderTotalRepository;

    @Autowired
    private ContextModifierRepository contextModifierRepository;

    @Autowired
    private LogisticService logisticService;

    @Transactional
    public OrderTotal createOrUpdate(OrderTotalDto orderTotalDto) {

        OrderTotal orderTotal = orderTotalDto.id() != null
                ? this.orderTotalRepository.findById(orderTotalDto.id()).orElseThrow(
                        () -> new EntityNotFoundException("Total não encontrado para ID: " + orderTotalDto.id()))
                : new OrderTotal();

        orderTotal.setBenefits(orderTotalDto.benefits());
        orderTotal.setDeliveryFee(orderTotalDto.deliveryFee());
        orderTotal.setOrderAmount(orderTotalDto.orderAmount());
        orderTotal.setSubTotal(orderTotalDto.subTotal());
        orderTotal.setAdditionalFees(orderTotalDto.additionalFees());

        return orderTotalRepository.save(orderTotal);
    }

    @Transactional
    public OrderTotal validateAndCalculateTotal(FullOrderDto orderDto, Merchant merchant) {
        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal additionalFees = BigDecimal.ZERO;
        BigDecimal benefits = BigDecimal.ZERO;
        BigDecimal deliveryFee = BigDecimal.ZERO;

        for (OrderItemDto itemDto : orderDto.items()) {
            UUID itemContextModifierId = itemDto.contextModifierId();
            ContextModifier cmItem = contextModifierRepository.findById(itemContextModifierId)
                    .orElseThrow(() -> new ValidationException("ContextModifier não encontrado: " + itemContextModifierId));

            BigDecimal basePrice = cmItem.getPrice().getValue();
            BigDecimal optionsTotal = BigDecimal.ZERO;

            if (itemDto.options() != null) {
                for (OrderItemOptionDto optionDto : itemDto.options()) {
                    UUID optionContextModifierId = optionDto.contextModifierId();
                    ContextModifier cmOption = contextModifierRepository.findById(optionContextModifierId)
                        .orElseThrow(() -> new ValidationException("ContextModifier não encontrado: " + optionContextModifierId));

                    BigDecimal price = cmOption.getPrice().getValue().multiply(BigDecimal.valueOf(optionDto.quantity()));
                    optionsTotal = optionsTotal.add(price);
                }
            }

            BigDecimal itemTotal = (basePrice.add(optionsTotal)).multiply(BigDecimal.valueOf(itemDto.quantity()));
            subtotal = subtotal.add(itemTotal);
        }

        if (orderDto.additionalFees() != null) {
            additionalFees = orderDto.additionalFees().stream()
                    .map(f -> f.value())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        if (orderDto.benefits() != null) {
            benefits = orderDto.benefits().stream()
                    .map(b -> b.value())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        if (orderDto.delivery() != null && orderDto.delivery().address() != null) {
            AddressDto origin = new AddressDto(merchant.getAddress());
            AddressDto destination = orderDto.delivery().address();
            RouteDto route = logisticService.calculateDistance(origin, destination, merchant.getLogisticSetting());
            deliveryFee = route.deliveryFee();
        }

        BigDecimal totalAmount = subtotal.add(additionalFees).add(deliveryFee).subtract(benefits);

        OrderTotalDto frontend = orderDto.total();
        if (frontend == null ||
            frontend.subTotal().compareTo(subtotal) != 0 ||
            frontend.deliveryFee().compareTo(deliveryFee) != 0 ||
            frontend.additionalFees().compareTo(additionalFees) != 0 ||
            frontend.benefits().compareTo(benefits) != 0 ||
            frontend.orderAmount().compareTo(totalAmount) != 0) {
            throw new ValidationException("Totais enviados não conferem com os valores calculados");
        }

        OrderTotal orderTotal = orderDto.total().id() != null
                ? this.orderTotalRepository.findById(orderDto.total().id()).orElseThrow(
                        () -> new EntityNotFoundException("Total não encontrado para ID: " + orderDto.total().id()))
                : new OrderTotal();

        orderTotal.setSubTotal(subtotal);
        orderTotal.setAdditionalFees(additionalFees);
        orderTotal.setBenefits(benefits);
        orderTotal.setDeliveryFee(deliveryFee);
        orderTotal.setOrderAmount(totalAmount);

        return orderTotalRepository.save(orderTotal);
    }


}
