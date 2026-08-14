package ru.practicum.shareit.request;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.practicum.shareit.ShareItTests;

@WebMvcTest(RequestController.class)
public class RequestTest extends ShareItTests {

    @MockBean
    protected RequestClient requestClient;
}
