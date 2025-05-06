package br.com.rotafood.api.order.application.service;


import br.com.rotafood.api.order.domain.repository.PaymentRecordMethodRepository;
import br.com.rotafood.api.order.application.dto.PaymentRecordMethodDto;
import br.com.rotafood.api.order.domain.entity.PaymentRecordMethod;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentRecordMethodService {

    @Autowired
    private PaymentRecordMethodRepository orderPaymentMethodRepository;


    @Transactional
    public PaymentRecordMethod createOrUpdate(PaymentRecordMethodDto dto) {

        PaymentRecordMethod method = dto.id() != null
                ? orderPaymentMethodRepository.findById(dto.id())
                        .orElseThrow(() -> new EntityNotFoundException("PaymentRecordMethod not found."))
                : new PaymentRecordMethod();

        method.setDescription(dto.description());
        method.setMethod(dto.method());
        method.setPaid(dto.paid());
        method.setType(dto.type());
        method.setValue(dto.value());
        method.setChangeFor(dto.changeFor());
        
        return orderPaymentMethodRepository.save(method);
    }

}
