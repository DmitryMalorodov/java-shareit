package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.marker.OnCreate;
import ru.practicum.shareit.request.model.ItemRequest;

import static ru.practicum.shareit.constant.message.ItemValidMessages.*;


/**
 * TODO Sprint add-controllers.
 */
@Data
@Builder(toBuilder = true)
public class ItemDto {
    private Long id;

    @NotBlank(groups = OnCreate.class, message = NAME_BLANK_MESSAGE)
    private String name;

    @NotBlank(groups = OnCreate.class, message = DESCRIPTION_BLANK_MESSAGE)
    private String description;

    @NotNull(groups = OnCreate.class, message = AVAILABLE_NULL_MESSAGE)
    private Boolean available;

    private Long ownerId;

    private ItemRequest request;
}
