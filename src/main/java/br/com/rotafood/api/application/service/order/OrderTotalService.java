package br.com.rotafood.api.application.service.order;

import br.com.rotafood.api.application.dto.order.OrderTotalDto;
import br.com.rotafood.api.domain.entity.order.OrderTotal;
import br.com.rotafood.api.domain.repository.OrderTotalRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderTotalService {

    @Autowired
    private OrderTotalRepository orderTotalRepository;

    @Transactional
    public OrderTotal createOrUpdate(OrderTotalDto orderTotalDto) {
    

        OrderTotal orderTotal = orderTotalDto.id() != null 
        ? this.orderTotalRepository.findById(orderTotalDto.id()).orElseThrow(() -> 
            new EntityNotFoundException("Total não encontrado para ID: " + orderTotalDto.id()))
        : new OrderTotal();

        orderTotal.setBenefits(orderTotalDto.benefits());
        orderTotal.setDeliveryFee(orderTotalDto.deliveryFee());
        orderTotal.setOrderAmount(orderTotalDto.orderAmount());
        orderTotal.setSubTotal(orderTotalDto.subTotal());
        orderTotal.setAdditionalFees(orderTotalDto.additionalFees());


        return orderTotalRepository.save(orderTotal);
    }

  
}
