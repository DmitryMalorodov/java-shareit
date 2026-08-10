package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.marker.OnCreate;

import static ru.practicum.shareit.constant.ValidMessages.NAME_BLANK_MESSAGE;
import static ru.practicum.shareit.constant.message.ItemValidMessages.AVAILABLE_NULL_MESSAGE;
import static ru.practicum.shareit.constant.message.ItemValidMessages.DESCRIPTION_BLANK_MESSAGE;

@Data
@Builder(toBuilder = true)
public class ReqItemDto {
    @NotBlank(groups = OnCreate.class, message = NAME_BLANK_MESSAGE)
    private String name;

    @NotBlank(groups = OnCreate.class, message = DESCRIPTION_BLANK_MESSAGE)
    private String description;

    @NotNull(groups = OnCreate.class, message = AVAILABLE_NULL_MESSAGE)
    private Boolean available;

    private Long requestId;
}
