package teamvoy.test.service;

import teamvoy.test.model.Order;

public interface OrderService {
    Order add(Order order);

    void remove();
}
