package ru.practicum.shareit.bookings;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.booking.dto.RespBookingDto;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
@DisplayName("Проверка сериализации/десериализации json")
public class BookingJsonTests {

    @Autowired
    private JacksonTester<RespBookingDto> json;

    private static final LocalDateTime START = LocalDateTime.now().withNano(0);
    private static final LocalDateTime END = LocalDateTime.now().plusDays(1).withNano(0);

    @Test
    void checkJson() throws IOException {
        RespBookingDto dto = RespBookingDto.builder()
                .id(1L)
                .start(START)
                .end(END)
                .status(BookingStatus.APPROVED)
                .build();

        //проверка сериализации
        JsonContent<RespBookingDto> jsonRes = json.write(dto);
        assertThat(jsonRes).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(jsonRes).extractingJsonPathStringValue("$.start").isEqualTo(START.toString());
        assertThat(jsonRes).extractingJsonPathStringValue("$.end").isEqualTo(END.toString());
        assertThat(jsonRes).extractingJsonPathStringValue("$.status").isEqualTo(dto.getStatus().name());

        //проверка десериализации
        RespBookingDto dtoRes = json.parse(jsonRes.getJson()).getObject();
        assertThat(dtoRes.getId()).isEqualTo(dto.getId());
        assertThat(dtoRes.getStart()).isEqualTo(dto.getStart());
        assertThat(dtoRes.getEnd()).isEqualTo(dto.getEnd());
        assertThat(dtoRes.getStatus()).isEqualTo(dto.getStatus());
    }
}
