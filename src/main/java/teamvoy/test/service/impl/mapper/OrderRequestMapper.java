package teamvoy.test.service.impl.mapper;

import java.time.LocalDateTime;
import org.springframework.stereotype.Component;
import teamvoy.test.model.Order;
import teamvoy.test.model.dto.OrderRequestDto;

@Component
public class OrderRequestMapper {
    public Order toModel(OrderRequestDto orderRequestDto) {
        Order order = new Order();
        order.setItem(orderRequestDto.getItem());
        order.setQuantity(orderRequestDto.getQuantity());
        order.setPrice(orderRequestDto.getPrice());
        order.setTime(LocalDateTime.now());
        return order;
    }
}
