package ru.practicum.shareit.requests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.request.dto.RespGetItemRequestsDto;
import ru.practicum.shareit.request.dto.RespItemRequestDto;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
@DisplayName("Проверка сериализации/десериализации json")
public class RequestJsonTests {

    @Autowired
    private JacksonTester<RespItemRequestDto> json;

    @Autowired
    private JacksonTester<RespGetItemRequestsDto> json2;

    private static final LocalDateTime DATE_TIME = LocalDateTime.now().withNano(0);

    @Test
    void checkJson() throws IOException {
        RespItemRequestDto dto = new RespItemRequestDto(1L, "text", 1L, DATE_TIME);

        //проверка сериализации
        JsonContent<RespItemRequestDto> jsonRes = json.write(dto);
        assertThat(jsonRes).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(jsonRes).extractingJsonPathStringValue("$.description").isEqualTo(dto.getDescription());
        assertThat(jsonRes).extractingJsonPathNumberValue("$.requestorId").isEqualTo(1);
        assertThat(jsonRes).extractingJsonPathStringValue("$.created").isEqualTo(dto.getCreated().toString());

        //проверка десериализации
        RespItemRequestDto dtoRes = json.parse(jsonRes.getJson()).getObject();
        assertThat(dtoRes.getId()).isEqualTo(dto.getId());
        assertThat(dtoRes.getDescription()).isEqualTo(dto.getDescription());
        assertThat(dtoRes.getRequestorId()).isEqualTo(dto.getRequestorId());
        assertThat(dtoRes.getCreated()).isEqualTo(dto.getCreated());
    }

    @Test
    void checkJson2() throws IOException {
        RespGetItemRequestsDto dto = new RespGetItemRequestsDto(1L, "text", DATE_TIME, List.of());

        //проверка сериализации
        JsonContent<RespGetItemRequestsDto> jsonRes = json2.write(dto);
        assertThat(jsonRes).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(jsonRes).extractingJsonPathStringValue("$.description").isEqualTo(dto.getDescription());
        assertThat(jsonRes).extractingJsonPathStringValue("$.created").isEqualTo(dto.getCreated().toString());
        assertThat(jsonRes).extractingJsonPathArrayValue("$.items").isEqualTo(List.of());

        //проверка десериализации
        RespGetItemRequestsDto dtoRes = json2.parse(jsonRes.getJson()).getObject();
        assertThat(dtoRes.getId()).isEqualTo(dto.getId());
        assertThat(dtoRes.getDescription()).isEqualTo(dto.getDescription());
        assertThat(dtoRes.getCreated()).isEqualTo(dto.getCreated());
        assertThat(dtoRes.getItems()).isEqualTo(dto.getItems());
    }
}
