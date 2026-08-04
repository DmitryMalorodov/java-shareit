package ru.practicum.shareit.item.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.dto.GetUserItemsDto;
import ru.practicum.shareit.item.dto.ReqItemDto;
import ru.practicum.shareit.item.dto.RespItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemMapper {

    public static RespItemDto toItemDto(Item item) {
        return RespItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .owner(item.getOwner())
                .build();
    }

    public static GetUserItemsDto toGetUserItemsDto(Item item) {
        return GetUserItemsDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .owner(item.getOwner())
                .build();
    }

    public static Item toItem(GetUserItemsDto getUserItemsDto) {
        return Item.builder()
                .id(getUserItemsDto.getId())
                .name(getUserItemsDto.getName())
                .description(getUserItemsDto.getDescription())
                .available(getUserItemsDto.getAvailable())
                .owner(getUserItemsDto.getOwner())
                .build();
    }

    public static Item toItem(ReqItemDto item, User user) {
        return Item.builder()
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .owner(user)
                .build();
    }
}
