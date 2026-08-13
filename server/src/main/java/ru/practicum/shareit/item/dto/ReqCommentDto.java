package ru.practicum.shareit.item.dto;

import lombok.*;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ReqCommentDto {
    private String text;
}
