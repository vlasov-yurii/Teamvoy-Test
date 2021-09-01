package teamvoy.test.service;

import teamvoy.test.model.Item;

public interface ItemService {
    Item add(Item item);

    Item getItem(String name, Long quantity);
}
