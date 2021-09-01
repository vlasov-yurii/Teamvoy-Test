package teamvoy.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import teamvoy.test.model.dto.ItemRequestDto;
import teamvoy.test.model.dto.ItemResponseDto;
import teamvoy.test.service.ItemService;
import teamvoy.test.service.impl.mapper.ItemRequestMapper;
import teamvoy.test.service.impl.mapper.ItemResponseMapper;

@RestController
@RequestMapping("/items")
public class ItemController {
    private ItemService itemService;
    private ItemResponseMapper itemResponseMapper;
    private ItemRequestMapper itemRequestMapper;

    public ItemController(ItemService itemService, ItemResponseMapper itemResponseMapper,
                          ItemRequestMapper itemRequestMapper) {
        this.itemService = itemService;
        this.itemResponseMapper = itemResponseMapper;
        this.itemRequestMapper = itemRequestMapper;
    }

    @PostMapping("/inject")
    public ItemResponseDto completeOrder(@RequestBody ItemRequestDto itemRequestDto) {
        return itemResponseMapper.toDto(itemService.add(itemRequestMapper.toModel(itemRequestDto)));
    }

    @GetMapping
    public ItemResponseDto getItem(@RequestParam String name, @RequestParam Long quantity) {
        return itemResponseMapper.toDto(itemService.getItem(name, quantity));
    }
}
