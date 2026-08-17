package ru.practicum.shareit.bookings;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;
import ru.practicum.shareit.ShareItTests;
import ru.practicum.shareit.booking.BookingClient;
import ru.practicum.shareit.booking.BookingController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(BookingController.class)
public class BookingTest extends ShareItTests {
    static final String BOOKINGS = "/bookings";
    static final String BOOKINGS_OWNER = "/bookings/owner";

    @MockBean
    protected BookingClient bookingClient;

    ResultActions getUserBookingsResAct(Long userId, String state) throws Exception {
        return mockMvc.perform(get(BOOKINGS)
                .queryParam("state", state)
                .header("X-Sharer-User-Id", userId));
    }

    ResultActions getUserItemsBookingsResAct(Long userId, String state) throws Exception {
        return mockMvc.perform(get(BOOKINGS_OWNER)
                .queryParam("state", state)
                .header("X-Sharer-User-Id", userId));
    }
}
