package ru.practicum.shareit.request.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.ReqItemRequestDto;
import ru.practicum.shareit.request.dto.RespGetItemRequestsDto;
import ru.practicum.shareit.request.dto.RespItemRequestDto;
import ru.practicum.shareit.request.dto.ResponseDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemRequestMapper {

    public static ItemRequest toItemRequest(ReqItemRequestDto itemRequestDto, User user) {
        return ItemRequest.builder()
                .description(itemRequestDto.getDescription())
                .requestor(user)
                .build();
    }

    public static ItemRequest toItemRequest(RespGetItemRequestsDto respGetItemRequestsDto) {
        return ItemRequest.builder()
                .id(respGetItemRequestsDto.getId())
                .description(respGetItemRequestsDto.getDescription())
                .created(respGetItemRequestsDto.getCreated())
                .build();
    }

    public static RespItemRequestDto toRespItemRequestDto(ItemRequest itemRequest) {
        return RespItemRequestDto.builder()
                .id(itemRequest.getId())
                .description(itemRequest.getDescription())
                .requestorId(itemRequest.getRequestor().getId())
                .created(itemRequest.getCreated())
                .build();
    }

    public static RespGetItemRequestsDto toRespGetItemRequestsDto(ItemRequest itemRequest) {
        return RespGetItemRequestsDto.builder()
                .id(itemRequest.getId())
                .description(itemRequest.getDescription())
                .created(itemRequest.getCreated())
                .items(toResponseDto(itemRequest.getItems()))
                .build();
    }

    private static Collection<ResponseDto> toResponseDto(Collection<Item> items) {
        return items.stream()
                .map(ItemRequestMapper::toResponseDto)
                .toList();
    }

    private static ResponseDto toResponseDto(Item item) {
        return ResponseDto.builder()
                .id(item.getId())
                .name(item.getName())
                .ownerId(item.getOwner().getId())
                .build();
    }
}
