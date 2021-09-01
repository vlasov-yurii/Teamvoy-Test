package teamvoy.test.controller;

import org.springframework.web.bind.annotation.*;
import teamvoy.test.model.dto.OrderRequestDto;
import teamvoy.test.model.dto.OrderResponseDto;
import teamvoy.test.service.OrderService;
import teamvoy.test.service.impl.mapper.OrderRequestMapper;
import teamvoy.test.service.impl.mapper.OrderResponseMapper;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private OrderService orderService;
    private OrderResponseMapper orderResponseMapper;
    private OrderRequestMapper orderRequestMapper;

    public OrderController(OrderService orderService, OrderResponseMapper orderResponseMapper,
                           OrderRequestMapper orderRequestMapper) {
        this.orderService = orderService;
        this.orderResponseMapper = orderResponseMapper;
        this.orderRequestMapper = orderRequestMapper;
    }

    @PostMapping("/inject")
    public OrderResponseDto completeOrder(@RequestBody OrderRequestDto orderRequestDto) {
        return orderResponseMapper.toDto(orderService
                .add(orderRequestMapper.toModel(orderRequestDto)));
    }

    @GetMapping("/remove")
    public void deleteOrder() {
        orderService.remove();
    }
}
