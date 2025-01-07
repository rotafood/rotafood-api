package br.com.rotafood.api.application.service.order;

import br.com.rotafood.api.application.dto.order.OrderCustomerDto;
import br.com.rotafood.api.domain.entity.order.Order;
import br.com.rotafood.api.domain.entity.order.OrderCustomer;
import br.com.rotafood.api.domain.repository.OrderCustomerRepository;
import br.com.rotafood.api.domain.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderCustomerService {

    @Autowired
    private OrderCustomerRepository orderCustomerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public OrderCustomer createOrAssociate(OrderCustomerDto orderCustomerDto, UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));

        OrderCustomer orderCustomer = orderCustomerDto.id() != null
                ? orderCustomerRepository.findById(orderCustomerDto.id())
                        .orElseThrow(() -> new EntityNotFoundException("OrderCustomer not found."))
                : new OrderCustomer();

        orderCustomer.setOrdersCountOnMerchant(orderCustomerDto.ordersCountOnMerchant());
        orderCustomer.setSegmentation(orderCustomerDto.segmentation());
        orderCustomer.setName(orderCustomerDto.name());
        orderCustomer.setDocument(orderCustomerDto.document());
        orderCustomer.setPhone(orderCustomerDto.phone());

        order.setCustomer(orderCustomer);

        orderRepository.save(order);

        if (orderCustomerDto.id() == null) {
            orderCustomerRepository.save(orderCustomer);
        }

        return orderCustomer;
    }

    public OrderCustomer getByOrderId(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));
        return order.getCustomer();
    }
}

