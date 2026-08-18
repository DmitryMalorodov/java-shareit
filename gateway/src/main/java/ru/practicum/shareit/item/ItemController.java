package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ReqCommentDto;
import ru.practicum.shareit.item.dto.ReqItemDto;

@RestController
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {
    private final ItemClient itemClient;

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItemById(
            @RequestHeader("X-Sharer-User-Id")
            @NotNull
            @Min(value = 1, message = "ID пользователя должен быть больше 0")
            final Long userId,
            @PathVariable final Long itemId
    ) {
        log.info("Get item by id={}, userId={}", itemId, userId);
        return itemClient.getItemById(itemId, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getUserItems(
            @RequestHeader("X-Sharer-User-Id")
            @NotNull
            @Min(value = 1, message = "ID пользователя должен быть больше 0")
            final Long userId
    ) {
        log.info("Get items of user, userId={}", userId);
        return itemClient.getUserItems(userId);
    }

    @PostMapping
    public ResponseEntity<Object> create(
            @RequestHeader("X-Sharer-User-Id")
            @NotNull
            @Min(value = 1, message = "ID пользователя должен быть больше 0")
            final Long userId,
            @Valid @RequestBody final ReqItemDto item
    ) {
        log.info("Create item {}, userId={}", item, userId);
        return itemClient.createItem(item, userId);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> update(
            @RequestHeader("X-Sharer-User-Id")
            @NotNull
            @Min(value = 1, message = "ID пользователя должен быть больше 0")
            final Long userId,
            @RequestBody final ReqItemDto item,
            @PathVariable final Long itemId
    ) {
        log.info("Update item with new data={}, itemId={}, userId={}", item, itemId, userId);
        return itemClient.updateItem(item, itemId, userId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> search(
            @RequestHeader("X-Sharer-User-Id")
            @NotNull
            @Min(value = 1, message = "ID пользователя должен быть больше 0")
            final Long userId,
            @RequestParam("text") final String text
    ) {
        log.info("Search item by text={}, userId={}", text, userId);
        return itemClient.searchItem(text, userId);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> create(
            @RequestHeader("X-Sharer-User-Id")
            @NotNull
            @Min(value = 1, message = "ID пользователя должен быть больше 0")
            final Long userId,
            @Valid @RequestBody final ReqCommentDto comment,
            @PathVariable final Long itemId
    ) {
        return itemClient.createComment(comment, itemId, userId);
    }
}
