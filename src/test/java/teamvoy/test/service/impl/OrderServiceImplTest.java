package teamvoy.test.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import teamvoy.test.dao.ItemRepository;
import teamvoy.test.dao.OrderRepository;
import teamvoy.test.exeption.DataProcessingException;
import teamvoy.test.model.Item;
import teamvoy.test.model.Order;
import teamvoy.test.service.OrderService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

class OrderServiceImplTest {
    private OrderService orderService;
    private OrderRepository orderRepository;
    private ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        orderRepository = Mockito.mock(OrderRepository.class);
        itemRepository = Mockito.mock(ItemRepository.class);
        orderService = new OrderServiceImpl(orderRepository, itemRepository);
    }

    @Test
    void add_ok() {
        String name = "apple";
        Long quantity = 5l;

        Item item = createItem(name, quantity);
        Order order = createOrder(name, quantity);

        Mockito.when(itemRepository.getItemByMinPrice(name, quantity))
                .thenReturn(Optional.of(item));
        Mockito.when(orderRepository.save(order)).thenReturn(order);

        Order actual = orderService.add(order);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(order.getItem(), actual.getItem());
        Assertions.assertEquals(order.getQuantity(), actual.getQuantity());
        Assertions.assertEquals(order.getPrice(), actual.getPrice());
    }

    @Test
    void add_NotOk() {
        Item item = createItem("apple", 5L);
        Order order = createOrder("grape", 2L);

        Mockito.when(itemRepository.getItemByMinPrice(item.getName(), item.getQuantity()))
                .thenReturn(Optional.of(item));
        Mockito.when(orderRepository.save(order)).thenReturn(order);

        try {
            orderService.add(order);
        } catch (DataProcessingException e) {
            Assertions.assertEquals("Sorry, this items is not in the DB", e.getMessage());
            return;
        }
        Assertions.fail("Expected to receive DataProcessingException");
    }

    private Item createItem(String name, Long quantity) {
        Item apple = new Item();
        apple.setName(name);
        apple.setQuantity(quantity);
        apple.setPrice(BigDecimal.valueOf(10));
        return apple;
    }

    private Order createOrder(String name, Long quantity) {
        Order order = new Order();
        order.setItem(name);
        order.setQuantity(quantity);
        order.setTime(LocalDateTime.now());
        return order;
    }
}
