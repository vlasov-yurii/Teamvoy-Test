package teamvoy.test.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;
import teamvoy.test.dao.ItemRepository;
import teamvoy.test.exeption.DataProcessingException;
import teamvoy.test.model.Item;
import teamvoy.test.service.ItemService;
import org.mockito.Mockito;
import java.math.BigDecimal;
import java.util.Optional;

class ItemServiceImplTest {
    private ItemService itemService;
    private ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        itemRepository = Mockito.mock(ItemRepository.class);
        itemService = new ItemServiceImpl(itemRepository);
    }

    @Test
    void getItem_Ok() {
        String name = "apple";
        Long[] quantity = {2L, 5L, 7l};
        Item firstApple = new Item();
        firstApple.setName(name);
        firstApple.setQuantity(5L);
        firstApple.setPrice(BigDecimal.valueOf(10));
        Item secondApple = new Item();
        secondApple.setName(name);
        secondApple.setQuantity(15L);
        secondApple.setPrice(BigDecimal.valueOf(25));

        for (int i = 0; i < quantity.length; i++) {
            mock(name, quantity[i], firstApple);
            if (quantity[i] <= firstApple.getQuantity()) {
                checked(name, quantity[i], firstApple);
            }
            if (quantity[i] > firstApple.getQuantity()) {
                checked(name, firstApple.getQuantity(), firstApple);
            }
        }
    }

    @Test
    void getItem_NotOk() {
        String name = "apple";
        Long quantity = 2L;
        Item apple = new Item();
        apple.setName(name);
        apple.setQuantity(5L);
        apple.setPrice(BigDecimal.valueOf(10));

        Mockito.when(itemRepository.getItemByMinPrice(name, quantity))
                .thenReturn(Optional.of(apple));

        try {
            itemService.getItem("grape", quantity);
        } catch (DataProcessingException e) {
            Assertions.assertEquals("Sorry, this items is not in the DB", e.getMessage());
            return;
        }
        Assertions.fail("Expected to receive DataProcessingException");
    }

    private OngoingStubbing<Optional<Item>> mock(String name, Long quantity, Item item) {
        if (quantity <= item.getQuantity()) {
            Item apple = new Item();
            apple.setName(name);
            apple.setQuantity(quantity);
            apple.setPrice(BigDecimal.valueOf(10));
            return Mockito.when(itemRepository.getItemByMinPrice(name, quantity))
                    .thenReturn(Optional.of(apple));
        }
        return Mockito.when(itemRepository.getItemByMinPrice(name, quantity))
                .thenReturn(Optional.of(item));
    }

    private void checked(String name, Long quantity, Item item) {
        Item actual = itemService.getItem(name, quantity);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(name, actual.getName());
        Assertions.assertEquals(quantity, actual.getQuantity());
        Assertions.assertEquals(item.getPrice(), actual.getPrice());
    }
}
