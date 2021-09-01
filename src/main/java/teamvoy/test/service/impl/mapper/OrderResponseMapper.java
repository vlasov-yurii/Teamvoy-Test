package teamvoy.test.service.impl.mapper;

import org.springframework.stereotype.Component;
import teamvoy.test.model.Order;
import teamvoy.test.model.dto.OrderResponseDto;

@Component
public class OrderResponseMapper {
    public OrderResponseDto toDto(Order order) {
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setId(order.getId());
        orderResponseDto.setItem(order.getItem());
        orderResponseDto.setQuantity(order.getQuantity());
        orderResponseDto.setPrice(order.getPrice());
        orderResponseDto.setTime(order.getTime());
        return orderResponseDto;
    }
}
