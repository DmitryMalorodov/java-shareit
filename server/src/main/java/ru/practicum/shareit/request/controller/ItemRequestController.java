package ru.practicum.shareit.request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ReqItemRequestDto;
import ru.practicum.shareit.request.dto.RespGetItemRequestsDto;
import ru.practicum.shareit.request.dto.RespItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.util.Collection;

/**
 * TODO Sprint add-item-requests.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
public class ItemRequestController {
    private final ItemRequestService itemRequestService;

    @PostMapping
    public RespItemRequestDto create(
            @RequestHeader("X-Sharer-User-Id") final Long userId,
            @RequestBody final ReqItemRequestDto itemRequest
    ) {
        return itemRequestService.createItemRequest(itemRequest, userId);
    }

    @GetMapping("/all")
    public Collection<RespItemRequestDto> getItemRequests(@RequestHeader("X-Sharer-User-Id") final Long userId) {
        return itemRequestService.getItemRequests(userId);
    }

    @GetMapping()
    public Collection<RespGetItemRequestsDto> getUserItemRequests(@RequestHeader("X-Sharer-User-Id") final Long userId) {
        return itemRequestService.getUserItemRequests(userId);
    }

    @GetMapping("/{requestId}")
    public RespGetItemRequestsDto getItemRequestById(
            @RequestHeader("X-Sharer-User-Id") final Long userId,
            @PathVariable final Long requestId
    ) {
        return itemRequestService.getItemRequestById(requestId);
    }
}
