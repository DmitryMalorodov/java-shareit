package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.service.ItemService;

import java.util.Collection;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/{itemId}")
    public GetUserItemsDto getItemById(
            @RequestHeader("X-Sharer-User-Id") final Long userId,
            @PathVariable final Long itemId
    ) {
        return itemService.getItemById(itemId, userId);
    }

    @GetMapping
    public Collection<GetUserItemsDto> getUserItems(@RequestHeader("X-Sharer-User-Id") final Long userId) {
        return itemService.getUserItems(userId);
    }


    @PostMapping
    public RespItemDto create(
            @RequestHeader("X-Sharer-User-Id") final Long userId,
            @RequestBody final ReqItemDto item
    ) {
        return itemService.create(item, userId);
    }

    @PatchMapping("/{itemId}")
    public RespItemDto update(
            @RequestHeader("X-Sharer-User-Id") final Long userId,
            @RequestBody final ReqItemDto item,
            @PathVariable final Long itemId
    ) {
        return itemService.update(item, userId, itemId);
    }

    @GetMapping("/search")
    public Collection<RespItemDto> search(
            @RequestHeader("X-Sharer-User-Id") final Long userId,
            @RequestParam("text") final String text
    ) {
        return itemService.search(text);
    }

    @PostMapping("/{itemId}/comment")
    public RespCommentDto create(
            @RequestHeader("X-Sharer-User-Id") final Long userId,
            @RequestBody final ReqCommentDto comment,
            @PathVariable final Long itemId
    ) {
        return itemService.create(comment, userId, itemId);
    }
}
