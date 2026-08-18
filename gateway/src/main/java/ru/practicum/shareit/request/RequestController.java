package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ReqItemRequestDto;

@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
@Validated
public class RequestController {
    private final RequestClient requestClient;

    @PostMapping
    public ResponseEntity<Object> create(
            @RequestHeader("X-Sharer-User-Id")
            @NotNull
            @Min(value = 1, message = "ID пользователя должен быть больше 0")
            final Long userId,
            @Valid @RequestBody final ReqItemRequestDto itemRequest
    ) {
        return requestClient.createRequest(itemRequest, userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getItemRequests(
            @RequestHeader("X-Sharer-User-Id")
            @NotNull
            @Min(value = 1, message = "ID пользователя должен быть больше 0")
            final Long userId
    ) {
        return requestClient.getItemRequests(userId);
    }

    @GetMapping()
    public ResponseEntity<Object> getUserItemRequests(
            @RequestHeader("X-Sharer-User-Id")
            @NotNull
            @Min(value = 1, message = "ID пользователя должен быть больше 0")
            final Long userId
    ) {
        return requestClient.getUserItemRequests(userId);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getItemRequestById(
            @RequestHeader("X-Sharer-User-Id")
            @NotNull
            @Min(value = 1, message = "ID пользователя должен быть больше 0")
            final Long userId,
            @PathVariable final Long requestId
    ) {
        return requestClient.getItemRequestById(requestId, userId);
    }
}
