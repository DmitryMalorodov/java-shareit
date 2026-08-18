package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.marker.OnCreate;
import ru.practicum.shareit.marker.OnUpdate;

import static ru.practicum.shareit.constant.ValidMessages.NAME_BLANK_MESSAGE;
import static ru.practicum.shareit.constant.message.UserValidationMessages.EMAIL_BLANK_MESSAGE;
import static ru.practicum.shareit.constant.message.UserValidationMessages.EMAIL_NOT_CORRECT_MESSAGE;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ReqUserDto {
    @NotBlank(groups = OnCreate.class, message = NAME_BLANK_MESSAGE)
    private String name;

    @NotBlank(groups = OnCreate.class, message = EMAIL_BLANK_MESSAGE)
    @Email(groups = {OnCreate.class, OnUpdate.class}, message = EMAIL_NOT_CORRECT_MESSAGE)
    private String email;
}
