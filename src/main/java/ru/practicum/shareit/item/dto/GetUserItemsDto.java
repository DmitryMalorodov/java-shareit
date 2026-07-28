package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.user.model.User;

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
}
