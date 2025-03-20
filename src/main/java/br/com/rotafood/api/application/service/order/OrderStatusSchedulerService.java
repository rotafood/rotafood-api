package br.com.rotafood.api.application.service.order;

// import java.time.Instant;
// import java.time.temporal.ChronoUnit;
// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

// import br.com.rotafood.api.domain.entity.order.Order;
// import br.com.rotafood.api.domain.entity.order.OrderStatus;
// import br.com.rotafood.api.domain.entity.order.OrderTiming;
// import br.com.rotafood.api.domain.repository.OrderRepository;

@Component
public class OrderStatusSchedulerService {

    // @Autowired
    // private OrderRepository orderRepository;

    @Scheduled(fixedDelay = 60000000)
    public void updateOrdersStatus() {
        // orderRepository.count();
        // Instant cutoffTime = Instant.now()
        //         .minus(2, ChronoUnit.HOURS)
        //         .minus(30, ChronoUnit.MINUTES);

        // List<Order> orders = orderRepository.findByPreparationStartDateTimeBeforeAndStatusNotAndTiming(
        //         cutoffTime, 
        //         OrderStatus.COMPLETED,
        //         OrderTiming.IMMEDIATE);

        // orders.forEach(order -> {
        //     order.setStatus(OrderStatus.COMPLETED);
        //     orderRepository.save(order);
        // });
    }
}
