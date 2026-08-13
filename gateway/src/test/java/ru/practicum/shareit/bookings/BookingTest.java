package ru.practicum.shareit.bookings;

import org.springframework.test.web.servlet.ResultActions;
import ru.practicum.shareit.ShareItTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class BookingTest extends ShareItTests {
    static final String BOOKINGS_OWNER = "/bookings/owner";

    ResultActions getUserItemsBookingsResAct(Long userId, String state) throws Exception {
        return mockMvc.perform(get(BOOKINGS_OWNER)
                .queryParam("state", state)
                .header("X-Sharer-User-Id", userId));
    }
}
