package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import ru.practicum.shareit.marker.OnCreate;

import static ru.practicum.shareit.constant.message.CommentValidMessages.*;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ReqCommentDto {
    @NotBlank(groups = OnCreate.class, message = TEXT_BLANK_MESSAGE)
    private String text;
}
