package teamvoy.test.service.impl.mapper;

import org.springframework.stereotype.Component;
import teamvoy.test.model.Item;
import teamvoy.test.model.dto.ItemRequestDto;

@Component
public class ItemRequestMapper {
    public Item toModel(ItemRequestDto itemRequestDto) {
        Item item = new Item();
        item.setName(itemRequestDto.getName());
        item.setQuantity(itemRequestDto.getQuantity());
        item.setPrice(itemRequestDto.getPrice());
        return item;
    }
}
