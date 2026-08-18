package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static ru.practicum.shareit.constant.message.CommentValidMessages.TEXT_BLANK_MESSAGE;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ReqCommentDto {
    @NotBlank(message = TEXT_BLANK_MESSAGE)
    private String text;
}
