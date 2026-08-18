package ru.practicum.shareit.items;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.item.dto.GetUserItemsDto;
import ru.practicum.shareit.item.dto.LastBookingDateDto;
import ru.practicum.shareit.item.dto.NextBookingDateDto;
import ru.practicum.shareit.item.dto.RespCommentDto;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
@DisplayName("Проверка сериализации/десериализации json")
public class ItemJsonTests {

    @Autowired
    private JacksonTester<GetUserItemsDto> json;

    @Autowired
    private JacksonTester<RespCommentDto> json2;

    private static final LocalDateTime START = LocalDateTime.of(
            2026, 8, 17, 20, 41, 15);
    private static final LocalDateTime END = LocalDateTime.of(
            2026, 8, 18, 20, 41, 15);

    @Test
    void checkJson() throws IOException {
        GetUserItemsDto dto = GetUserItemsDto.builder()
                .id(1L)
                .available(true)
                .nextBooking(NextBookingDateDto.builder().start(START).end(END).build())
                .lastBooking(LastBookingDateDto.builder().start(START).end(END).build())
                .build();

        //проверка сериализации
        JsonContent<GetUserItemsDto> jsonRes = json.write(dto);
        assertThat(jsonRes).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(jsonRes).extractingJsonPathBooleanValue("$.available").isEqualTo(dto.getAvailable());
        assertThat(jsonRes).extractingJsonPathStringValue("$.nextBooking.start").isEqualTo(START.toString());
        assertThat(jsonRes).extractingJsonPathStringValue("$.nextBooking.end").isEqualTo(END.toString());
        assertThat(jsonRes).extractingJsonPathStringValue("$.lastBooking.start").isEqualTo(START.toString());
        assertThat(jsonRes).extractingJsonPathStringValue("$.lastBooking.end").isEqualTo(END.toString());

        //проверка десериализации
        GetUserItemsDto dtoRes = json.parse(jsonRes.getJson()).getObject();
        assertThat(dtoRes.getId()).isEqualTo(dto.getId());
        assertThat(dtoRes.getAvailable()).isEqualTo(dto.getAvailable());
        assertThat(dtoRes.getNextBooking().getStart()).isEqualTo(dto.getNextBooking().getStart());
        assertThat(dtoRes.getNextBooking().getEnd()).isEqualTo(dto.getNextBooking().getEnd());
        assertThat(dtoRes.getLastBooking().getStart()).isEqualTo(dto.getLastBooking().getStart());
        assertThat(dtoRes.getLastBooking().getEnd()).isEqualTo(dto.getLastBooking().getEnd());
    }

    @Test
    void checkJson2() throws IOException {
        RespCommentDto dto = RespCommentDto.builder()
                .id(1L)
                .text("text")
                .authorName("author")
                .created(START)
                .build();

        //проверка сериализации
        JsonContent<RespCommentDto> jsonRes = json2.write(dto);
        assertThat(jsonRes).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(jsonRes).extractingJsonPathStringValue("$.text").isEqualTo(dto.getText());
        assertThat(jsonRes).extractingJsonPathStringValue("$.authorName").isEqualTo(dto.getAuthorName());
        assertThat(jsonRes).extractingJsonPathStringValue("$.created").isEqualTo(START.toString());

        //проверка десериализации
        RespCommentDto dtoRes = json2.parse(jsonRes.getJson()).getObject();
        assertThat(dtoRes.getId()).isEqualTo(dto.getId());
        assertThat(dtoRes.getText()).isEqualTo(dto.getText());
        assertThat(dtoRes.getAuthorName()).isEqualTo(dto.getAuthorName());
        assertThat(dtoRes.getCreated()).isEqualTo(dto.getCreated());
    }
}
