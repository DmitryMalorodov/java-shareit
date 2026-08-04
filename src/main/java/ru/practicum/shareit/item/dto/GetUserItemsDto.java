package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder(toBuilder = true)
public class GetUserItemsDto {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private User owner;
    private NextBookingDateDto nextBooking;
    private LastBookingDateDto lastBooking;

    @Builder.Default
    private Collection<RespCommentDto> comments = new ArrayList<>();
}
