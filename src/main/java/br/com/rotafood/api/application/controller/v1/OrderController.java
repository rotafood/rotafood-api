package br.com.rotafood.api.application.controller.v1;

import br.com.rotafood.api.application.dto.order.FullOrderDto;
import br.com.rotafood.api.application.service.order.OrderService;
import br.com.rotafood.api.domain.entity.order.Order;
import br.com.rotafood.api.domain.entity.order.OrderType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ApiVersion.VERSION + "/merchants/{merchantId}/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<FullOrderDto> getAllOrders(            
        @PathVariable UUID merchantId,
        @RequestParam(required = false) List<OrderType> orderTypes,
        @RequestParam(required = false) Boolean isCompleted) {

    return orderService.getAllByFilters(merchantId, orderTypes, isCompleted)
            .stream()
            .map(FullOrderDto::new)
            .toList();
    }


    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable UUID merchantId, @PathVariable UUID orderId) {
        return orderService.getByIdAndMerchantId(orderId, merchantId);
    }

    @PutMapping
    public Order createOrUpdateOrder(
            @PathVariable UUID merchantId,
            @RequestBody @Valid FullOrderDto fullOrderDto) {
        return orderService.createOrUpdate(fullOrderDto, merchantId);
    }


    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable UUID merchantId, @PathVariable UUID orderId) {
        orderService.deleteByIdAndMerchantId(orderId, merchantId);
    }
}
