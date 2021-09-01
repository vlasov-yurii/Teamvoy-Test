package teamvoy.test.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import teamvoy.test.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("FROM Item i WHERE i.name = :name "
            + "AND i.quantity >= :quantity "
            + "AND i.price = (SELECT MIN(i.price) FROM i)")
    Optional<Item> getItemByMinPrice(@Param("name") String name, @Param("quantity") Long quantity);

    @Query("FROM Item i WHERE i.name LIKE :name AND i.price = (SELECT MIN(i.price) FROM i)")
    Optional<Item> getItemByName(@Param("name") String name);
}
