package teamvoy.test.service.impl.mapper;

import org.springframework.stereotype.Component;
import teamvoy.test.model.Item;
import teamvoy.test.model.dto.ItemResponseDto;

@Component
public class ItemResponseMapper {
    public ItemResponseDto toDto(Item item) {
        ItemResponseDto itemResponseDto = new ItemResponseDto();
        itemResponseDto.setId(item.getId());
        itemResponseDto.setName(item.getName());
        itemResponseDto.setQuantity(item.getQuantity());
        itemResponseDto.setPrice(item.getPrice());
        return itemResponseDto;
    }
}
