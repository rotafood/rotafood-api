package br.com.rotafood.api.application.service.order;


import br.com.rotafood.api.application.dto.order.PaymentRecordMethodDto;
import br.com.rotafood.api.domain.entity.order.*;
import br.com.rotafood.api.domain.repository.*;
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
        method.setPrepaid(dto.prepaid());
        method.setType(dto.type());
        method.setValue(dto.value());
        method.setChangeFor(dto.changeFor());
        
        return orderPaymentMethodRepository.save(method);
    }

}
