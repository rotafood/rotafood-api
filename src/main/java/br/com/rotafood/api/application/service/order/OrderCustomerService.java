package br.com.rotafood.api.application.service.order;

import br.com.rotafood.api.application.dto.order.OrderCustomerDto;
import br.com.rotafood.api.domain.entity.order.OrderCustomer;
import br.com.rotafood.api.domain.repository.OrderCustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderCustomerService {

    @Autowired
    private OrderCustomerRepository orderCustomerRepository;


    @Transactional
    public OrderCustomer createOrUpdate(OrderCustomerDto orderCustomerDto) {

        OrderCustomer orderCustomer = orderCustomerDto.id() != null
                ? orderCustomerRepository.findById(orderCustomerDto.id())
                        .orElseThrow(() -> new EntityNotFoundException("OrderCustomer not found."))
                : new OrderCustomer();

        orderCustomer.setSegmentation(orderCustomerDto.segmentation());
        orderCustomer.setName(orderCustomerDto.name());
        orderCustomer.setDocument(orderCustomerDto.document());
        orderCustomer.setPhone(orderCustomerDto.phone());

        return orderCustomerRepository.save(orderCustomer);
    }
}

