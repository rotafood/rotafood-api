package br.com.rotafood.api.order.application.service;

import br.com.rotafood.api.order.application.dto.PaymentRecordDto;
import br.com.rotafood.api.order.domain.entity.PaymentRecord;
import br.com.rotafood.api.order.domain.repository.PaymentRecordRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PaymentRecordService {

    @Autowired
    private PaymentRecordRepository orderPaymentRepository;

    @Autowired
    private PaymentRecordMethodService orderPaymentMethodService;

    @Transactional
    public PaymentRecord createOrUpdate(PaymentRecordDto dto) {

        PaymentRecord payment = dto.id() != null 
        ? this.orderPaymentRepository.findById(dto.id()).orElseThrow(() -> 
            new EntityNotFoundException("Pagamento não encontrado para ID: " + dto.id()))
        : new PaymentRecord();
    
        payment.setDescription(dto.description());
        payment.setPending(dto.pending());
        payment.setPaid(dto.paid());

        orderPaymentRepository.save(payment);

        payment.getMethods().clear();
        var methods = dto.methods().stream().map(orderPaymentMethodService::createOrUpdate).toList();
        payment.getMethods().addAll(methods);
        
        return orderPaymentRepository.save(payment);
    }
}
