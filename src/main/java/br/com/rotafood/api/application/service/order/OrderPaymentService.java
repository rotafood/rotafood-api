package br.com.rotafood.api.application.service.order;

import br.com.rotafood.api.application.dto.order.OrderPaymentDto;
import br.com.rotafood.api.domain.entity.order.OrderPayment;
import br.com.rotafood.api.domain.repository.OrderPaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderPaymentService {

    @Autowired
    private OrderPaymentRepository orderPaymentRepository;

    @Autowired
    private OrderPaymentMethodService orderPaymentMethodService;

    @Transactional
    public OrderPayment createOrUpdate(OrderPaymentDto dto) {

        OrderPayment payment = dto.id() != null 
        ? this.orderPaymentRepository.findById(dto.id()).orElseThrow(() -> 
            new EntityNotFoundException("Pagamento não encontrado para ID: " + dto.id()))
        : new OrderPayment();
    
        payment.setDescription(dto.description());
        payment.setPending(dto.pending());
        payment.setPrepaid(dto.prepaid());

        orderPaymentRepository.save(payment);

        payment.getMethods().clear();
        var methods = dto.methods().stream().map(orderPaymentMethodService::createOrUpdate).toList();
        payment.getMethods().addAll(methods);
        
        return orderPaymentRepository.save(payment);
    }
}
