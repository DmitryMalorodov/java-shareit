package ru.practicum.shareit.item.controller;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.GetUserItemsDto;
import ru.practicum.shareit.item.dto.ReqItemDto;
import ru.practicum.shareit.item.dto.RespItemDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.marker.OnCreate;

import java.util.Collection;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/{itemId}")
    public RespItemDto getItemById(
            @RequestHeader("X-Sharer-User-Id")
            @NotNull
            @Min(value = 1, message = "ID пользователя должен быть больше 0")
            final Long userId,
            @PathVariable final Long itemId
    ) {
        return itemService.getItemById(itemId);
    }

    @GetMapping
    public Collection<GetUserItemsDto> getUserItems(
            @RequestHeader("X-Sharer-User-Id")
            @NotNull
            @Min(value = 1, message = "ID пользователя должен быть больше 0")
            final Long userId
    ) {
        return itemService.getUserItems(userId);
    }


    @PostMapping
    public RespItemDto create(
            @RequestHeader("X-Sharer-User-Id")
            @NotNull
            @Min(value = 1, message = "ID пользователя должен быть больше 0")
            final Long userId,
            @Validated(OnCreate.class) @RequestBody final ReqItemDto item
    ) {
        return itemService.create(item, userId);
    }

    @PatchMapping("/{itemId}")
    public RespItemDto update(
            @RequestHeader("X-Sharer-User-Id")
            @NotNull
            @Min(value = 1, message = "ID пользователя должен быть больше 0")
            final Long userId,
            @RequestBody final ReqItemDto item,
            @PathVariable final Long itemId
    ) {
        return itemService.update(item, userId, itemId);
    }

    @GetMapping("/search")
    public Collection<RespItemDto> search(
            @RequestHeader("X-Sharer-User-Id")
            @NotNull
            @Min(value = 1, message = "ID пользователя должен быть больше 0")
            final Long userId,
            @RequestParam("text") final String text
    ) {
        return itemService.search(text);
    }
}
