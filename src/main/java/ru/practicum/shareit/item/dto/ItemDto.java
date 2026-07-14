package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.marker.OnCreate;
import ru.practicum.shareit.request.model.ItemRequest;

/**
 * TODO Sprint add-controllers.
 */
@Data
@Builder
public class ItemDto {
    private Long id;

    @NotBlank(groups = OnCreate.class, message = "Имя не может быть пустым")
    private String name;

    @NotBlank(groups = OnCreate.class, message = "Описание не может быть пустым")
    private String description;

    @NotNull(groups = OnCreate.class, message = "Статус доступности к аренде не может быть пустым")
    private Boolean available;

    private Long ownerId;

    private ItemRequest request;
}
