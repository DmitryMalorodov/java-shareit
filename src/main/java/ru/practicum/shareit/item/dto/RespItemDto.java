package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.user.model.User;

/**
 * TODO Sprint add-controllers.
 */
@Data
@Builder(toBuilder = true)
public class RespItemDto {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private User owner;
    //private ItemRequest request;
}
