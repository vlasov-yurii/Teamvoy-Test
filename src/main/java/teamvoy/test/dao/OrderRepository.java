package teamvoy.test.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamvoy.test.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
