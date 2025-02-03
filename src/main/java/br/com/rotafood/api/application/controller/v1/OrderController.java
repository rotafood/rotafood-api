package br.com.rotafood.api.application.controller.v1;

import br.com.rotafood.api.application.dto.PaginationDto;
import br.com.rotafood.api.application.dto.order.FullOrderDto;
import br.com.rotafood.api.application.dto.order.OrderDto;
import br.com.rotafood.api.application.service.order.OrderService;
import br.com.rotafood.api.domain.entity.order.OrderStatus;
import br.com.rotafood.api.domain.entity.order.OrderType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<PaginationDto<OrderDto>> getAllOrders(            
        @PathVariable UUID merchantId,
        @RequestParam(required = false) List<OrderType> orderTypes,
        @RequestParam(required = false) List<OrderStatus> orderStatuses,
        @RequestParam(required = false) String startDate,
        @RequestParam(required = false) String endDate,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "createdAt") String sortBy,
        @RequestParam(defaultValue = "desc") String sortDirection) {
        

        Pageable pageable = PageRequest.of(page, size, Sort.by(
            sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy));

        Page<OrderDto> orders = orderService.getAllByFilters(
            merchantId, orderTypes, orderStatuses, startDate, endDate, pageable
        ).map(OrderDto::new);

        return ResponseEntity.ok(PaginationDto.fromPage(orders, "/orders"));
    }



    @GetMapping("/{orderId}")
    public FullOrderDto getOrderById(@PathVariable UUID merchantId, @PathVariable UUID orderId) {
        return new FullOrderDto(orderService.getByIdAndMerchantId(orderId, merchantId));
    }

    @PutMapping
    public FullOrderDto createOrUpdateOrder(
            @PathVariable UUID merchantId,
            @RequestBody @Valid FullOrderDto fullOrderDto) {
        return new FullOrderDto(orderService.createOrUpdate(fullOrderDto, merchantId));
    }


    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable UUID merchantId, @PathVariable UUID orderId) {
        orderService.deleteByIdAndMerchantId(orderId, merchantId);
    }
}
