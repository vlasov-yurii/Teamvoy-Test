package teamvoy.test.service.impl;

import java.util.Optional;
import org.springframework.stereotype.Service;
import teamvoy.test.dao.ItemRepository;
import teamvoy.test.exeption.DataProcessingException;
import teamvoy.test.model.Item;
import teamvoy.test.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
    private ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Item add(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Item getItem(String name, Long quantity) {
        Optional<Item> itemByMinPrice = itemRepository.getItemByMinPrice(name, quantity);
        if (itemByMinPrice.isEmpty()) {
            Optional<Item> item = itemRepository.getItemByName(name);
            if (item.isEmpty()) {
                throw new DataProcessingException("Sorry, this items is not in the DB");
            }
            itemRepository.delete(item.get());
            return item.get();
        }
        Item item = itemByMinPrice.get();
        if (item.getQuantity() - quantity <= 0) {
            itemRepository.delete(item);
            return item;
        }
        Item newItem = item.clone(item);
        newItem.setQuantity(item.getQuantity() - quantity);
        itemRepository.save(newItem);
        item.setQuantity(quantity);
        return item;
    }
}
