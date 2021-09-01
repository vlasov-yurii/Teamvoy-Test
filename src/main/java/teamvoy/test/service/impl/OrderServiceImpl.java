package teamvoy.test.service.impl;

import org.springframework.stereotype.Service;
import teamvoy.test.dao.ItemRepository;
import teamvoy.test.dao.OrderRepository;
import teamvoy.test.exeption.DataProcessingException;
import teamvoy.test.model.Item;
import teamvoy.test.model.Order;
import teamvoy.test.service.OrderService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private ItemRepository itemRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public Order add(Order order) {
        Optional<Item> itemByMinPrice = itemRepository.getItemByMinPrice(order.getItem(), order.getQuantity());
        if (itemByMinPrice.isEmpty()) {
            Optional<Item> item = itemRepository.getItemByName(order.getItem());
            if (item.isEmpty()) {
                throw new DataProcessingException("Sorry, this items is not in the DB");
            }
            return orderRepository.save(setParametersByOrder(item, order));
        }
        if (itemByMinPrice.get().getQuantity() - order.getQuantity() <= 0) {
            return orderRepository.save(setParametersByOrder(itemByMinPrice, order));
        }
        Item newItem = itemByMinPrice.get().clone(itemByMinPrice.get());
        newItem.setQuantity(itemByMinPrice.get().getQuantity() - order.getQuantity());
        itemRepository.save(newItem);
        order.setPrice(itemByMinPrice.get().getPrice());
        return orderRepository.save(order);
    }

    @Override
    public void remove() {
        List<Order> allOrders = orderRepository.findAll();
        for (Order order: allOrders) {
            if (order.getTime().plusMinutes(10).isBefore(LocalDateTime.now())) {
                orderRepository.delete(order);
            }
        }
    }

    private Order setParametersByOrder(Optional<Item> item, Order order) {
        order.setQuantity(item.get().getQuantity());
        order.setPrice(item.get().getPrice());
        itemRepository.delete(item.get());
        return order;
    }
}
