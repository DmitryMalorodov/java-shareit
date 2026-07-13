package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.marker.OnCreate;
import ru.practicum.shareit.marker.OnUpdate;

@Data
@Builder
public class UserDto {
    @NotNull(groups = OnUpdate.class, message = "id не может быть пустым")
    private Long id;

    @NotBlank(groups = OnCreate.class, message = "Имя не может быть пустым")
    private String name;

    @NotBlank(groups = OnCreate.class, message = "email не может быть пустым")
    @Email(groups = {OnCreate.class, OnUpdate.class}, message = "Электронная почта не соответствует требуемому формату")
    private String email;
}
