package br.com.rotafood.api.application.service.order;


import br.com.rotafood.api.application.dto.order.OrderPaymentMethodDto;
import br.com.rotafood.api.domain.entity.order.*;
import br.com.rotafood.api.domain.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderPaymentMethodService {

    @Autowired
    private OrderPaymentMethodRepository orderPaymentMethodRepository;


    @Transactional
    public OrderPaymentMethod createOrUpdate(OrderPaymentMethodDto dto) {

        OrderPaymentMethod method = dto.id() != null
                ? orderPaymentMethodRepository.findById(dto.id())
                        .orElseThrow(() -> new EntityNotFoundException("OrderPaymentMethod not found."))
                : new OrderPaymentMethod();

        method.setDescription(dto.description());
        method.setMethod(dto.method());
        method.setPrepaid(dto.prepaid());
        method.setType(dto.type());
        method.setValue(dto.value());
        
        return orderPaymentMethodRepository.save(method);
    }

}
