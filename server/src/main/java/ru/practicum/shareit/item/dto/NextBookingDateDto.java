package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
public class NextBookingDateDto {
    private LocalDateTime start;
    private LocalDateTime end;
}
