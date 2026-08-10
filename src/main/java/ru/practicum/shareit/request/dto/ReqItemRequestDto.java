package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static ru.practicum.shareit.constant.ValidMessages.DESCRIPTION_BLANK_MESSAGE;

/**
 * TODO Sprint add-item-requests.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ReqItemRequestDto {
    @NotBlank(message = DESCRIPTION_BLANK_MESSAGE)
    private String description;
}
