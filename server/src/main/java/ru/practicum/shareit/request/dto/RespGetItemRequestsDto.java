package ru.practicum.shareit.request.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
@Builder(toBuilder = true)
public class RespGetItemRequestsDto {
    private Long id;
    private String description;
    private LocalDateTime created;
    private Collection<ResponseDto> items;
}
