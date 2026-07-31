package ru.practicum.shareit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.assertj.core.api.SoftAssertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.practicum.shareit.booking.dto.ReqBookingDto;
import ru.practicum.shareit.booking.dto.RespBookingDto;
import ru.practicum.shareit.item.dto.ReqItemDto;
import ru.practicum.shareit.item.dto.RespItemDto;
import ru.practicum.shareit.user.dto.ReqUserDto;
import ru.practicum.shareit.user.dto.RespUserDto;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.GeneralAssertions.isEqualTo;
import static ru.practicum.shareit.GeneralAssertions.isNotNull;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ShareItTests {
	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;

	protected void checkValidationError(ResultActions response, String expMessage) throws Exception {
		response
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error").value(expMessage));
	}

	protected void checkNotFoundError(ResultActions response, String expMessage) throws Exception {
		response
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.error").value(expMessage));
	}

	protected Long getIdFromObject(ResultActions response) throws Exception {
		return JsonPath.parse(response
				.andReturn()
				.getResponse()
				.getContentAsString()).read("$.id", Long.class);
	}

	protected ResultActions createUser(ReqUserDto user) throws Exception {
		return createUserResAct(user);
	}

	protected RespUserDto createUserDto(ReqUserDto user) throws Exception {
		String jsonResponse = createUserResAct(user)
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();

		return objectMapper.readValue(jsonResponse, RespUserDto.class);
	}

	private ResultActions createUserResAct(ReqUserDto user) throws Exception {
		return mockMvc.perform(post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)));
	}

	protected ResultActions createItem(ReqItemDto item, Long userId) throws Exception {
		return createItemResAct(item, userId);
	}

	protected RespItemDto createItemDto(ReqItemDto item, Long userId) throws Exception {
		String jsonResponse = createItemResAct(item, userId)
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();

		return objectMapper.readValue(jsonResponse, RespItemDto.class);
	}

	private ResultActions createItemResAct(ReqItemDto item, Long userId) throws Exception {
		return mockMvc.perform(post("/items")
				.contentType(MediaType.APPLICATION_JSON)
				.header("X-Sharer-User-Id", userId)
				.content(objectMapper.writeValueAsString(item)));
	}

	protected ResultActions createBooking(ReqBookingDto booking, Long userId) throws Exception {
		return createBookingResAct(booking, userId);
	}

	protected RespBookingDto createBookingDto(ReqBookingDto booking, Long userId) throws Exception {
		String jsonResponse = createBookingResAct(booking, userId)
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();

		return objectMapper.readValue(jsonResponse, RespBookingDto.class);
	}

	private ResultActions createBookingResAct(ReqBookingDto booking, Long userId) throws Exception {
		return mockMvc.perform(post("/bookings")
				.contentType(MediaType.APPLICATION_JSON)
				.header("X-Sharer-User-Id", userId)
				.content(objectMapper.writeValueAsString(booking)));
	}

	protected List<RespUserDto> getUsers() throws Exception {
		String jsonResponse = mockMvc.perform(get("/users"))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();

		return objectMapper.readValue(jsonResponse, new TypeReference<>() {});
	}

	protected RespUserDto getUser(Long userId) throws Exception {
		String jsonResponse = mockMvc.perform(get("/users/{id}", userId))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();

		return objectMapper.readValue(jsonResponse, RespUserDto.class);
	}

	protected void checkUser(RespUserDto actUser, ReqUserDto expUser) {
		SoftAssertions softAssert = new SoftAssertions();

		isNotNull(actUser.getId(),
				"ID пользователя '%d' отсутствует", softAssert);
		isEqualTo(actUser.getName(), expUser.getName(),
				"Имя пользователя '%s' не совпадает с ожидаемым '%s'", softAssert);
		isEqualTo(actUser.getEmail(), expUser.getEmail(),
				"Email пользователя '%s' не совпадает с ожидаемым '%s'", softAssert);

		softAssert.assertAll();
	}
}
