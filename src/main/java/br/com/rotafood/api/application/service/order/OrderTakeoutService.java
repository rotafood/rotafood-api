package br.com.rotafood.api.application.service.order;

import br.com.rotafood.api.application.dto.order.OrderTakeoutDto;
import br.com.rotafood.api.domain.entity.order.OrderTakeout;
import br.com.rotafood.api.domain.repository.OrderTakeoutRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderTakeoutService {

    @Autowired
    private OrderTakeoutRepository orderTakeoutRepository;

    @Transactional
    public OrderTakeout createOrUpdate(OrderTakeoutDto orderTakeoutDto) {

        OrderTakeout takeout = orderTakeoutDto.id() != null
                ? orderTakeoutRepository.findById(orderTakeoutDto.id())
                        .orElseThrow(() -> new EntityNotFoundException("OrderTakeout not found."))
                : new OrderTakeout();

        takeout.setTakeoutDateTime(orderTakeoutDto.takeoutDateTime());
        takeout.setComments(orderTakeoutDto.comments());

        return orderTakeoutRepository.save(takeout);
    }
}
