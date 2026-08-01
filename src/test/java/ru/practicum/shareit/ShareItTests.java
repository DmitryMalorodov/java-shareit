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
import ru.practicum.shareit.item.dto.GetUserItemsDto;
import ru.practicum.shareit.item.dto.ReqItemDto;
import ru.practicum.shareit.item.dto.RespCommentDto;
import ru.practicum.shareit.item.dto.RespItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dto.ReqUserDto;
import ru.practicum.shareit.user.dto.RespUserDto;
import ru.practicum.shareit.user.model.User;

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
		return mockMvc.perform(post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)));
	}

	protected RespUserDto createUserDto(ReqUserDto user) throws Exception {
		String jsonResponse = createUser(user)
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();

		return objectMapper.readValue(jsonResponse, RespUserDto.class);
	}

	protected ResultActions createItem(ReqItemDto item, Long userId) throws Exception {
		return mockMvc.perform(post("/items")
				.contentType(MediaType.APPLICATION_JSON)
				.header("X-Sharer-User-Id", userId)
				.content(objectMapper.writeValueAsString(item)));
	}

	protected RespItemDto createItemDto(ReqItemDto item, Long userId) throws Exception {
		String jsonResponse = createItem(item, userId)
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();

		return objectMapper.readValue(jsonResponse, RespItemDto.class);
	}

	protected ResultActions createBooking(ReqBookingDto booking, Long userId) throws Exception {
		return mockMvc.perform(post("/bookings")
				.contentType(MediaType.APPLICATION_JSON)
				.header("X-Sharer-User-Id", userId)
				.content(objectMapper.writeValueAsString(booking)));
	}

	protected RespBookingDto createBookingDto(ReqBookingDto booking, Long userId) throws Exception {
		String jsonResponse = createBooking(booking, userId)
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();

		return objectMapper.readValue(jsonResponse, RespBookingDto.class);
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

	protected void checkItem(RespItemDto actItem, ReqItemDto expItem, RespUserDto expOwner, List<RespCommentDto> comments) {
		SoftAssertions softAssert = new SoftAssertions();

		isNotNull(actItem.getId(), "ID вещи '%d' отсутствует", softAssert);
		isEqualTo(actItem.getName(), expItem.getName(),
				"Имя вещи '%s' не совпадает с ожидаемым '%s'", softAssert);
		isEqualTo(actItem.getDescription(), expItem.getDescription(),
				"Описание вещи '%s' не совпадает с ожидаемым '%s'", softAssert);
		isEqualTo(actItem.getAvailable(), expItem.getAvailable(),
				"Доступность вещи '%b' не совпадает с ожидаемой '%b'", softAssert);
		isEqualTo(actItem.getComments(), comments,
				"Список комментариев не совпадает с ожидаемым", softAssert);
		checkUser(actItem.getOwner(), expOwner, softAssert);

		softAssert.assertAll();
	}

	protected void checkItem(GetUserItemsDto actItem, ReqItemDto expItem, RespUserDto expOwner, List<RespCommentDto> comments) {
		SoftAssertions softAssert = new SoftAssertions();

		isNotNull(actItem.getId(), "ID вещи '%d' отсутствует", softAssert);
		isEqualTo(actItem.getName(), expItem.getName(),
				"Имя вещи '%s' не совпадает с ожидаемым '%s'", softAssert);
		isEqualTo(actItem.getDescription(), expItem.getDescription(),
				"Описание вещи '%s' не совпадает с ожидаемым '%s'", softAssert);
		isEqualTo(actItem.getAvailable(), expItem.getAvailable(),
				"Доступность вещи '%b' не совпадает с ожидаемой '%b'", softAssert);
		isEqualTo(actItem.getComments(), comments,
				"Список комментариев не совпадает с ожидаемым", softAssert);
		checkUser(actItem.getOwner(), expOwner, softAssert);

		softAssert.assertAll();
	}

	protected void checkItem(Item actItem, RespItemDto expItem, RespUserDto expOwner, SoftAssertions softAssert) {
		isEqualTo(actItem.getId(), expItem.getId(),
				"ID вещи '%d' не совпадает с ожидаемым '%d'", softAssert);
		isEqualTo(actItem.getName(), expItem.getName(),
				"Имя вещи '%s' не совпадает с ожидаемым '%s'", softAssert);
		isEqualTo(actItem.getDescription(), expItem.getDescription(),
				"Описание вещи '%s' не совпадает с ожидаемым '%s'", softAssert);
		isEqualTo(actItem.getAvailable(), expItem.getAvailable(),
				"Доступность вещи '%b' не совпадает с ожидаемой '%b'", softAssert);
		checkUser(actItem.getOwner(), expOwner, softAssert);
	}

	protected void checkUser(User actUser, RespUserDto expUser, SoftAssertions softAssert) {
		isEqualTo(actUser.getId(), expUser.getId(),
				"ID пользователя '%d' не совпадает с ожидаемым '%d'", softAssert);
		isEqualTo(actUser.getName(), expUser.getName(),
				"Имя пользователя '%s' не совпадает с ожидаемым '%s'", softAssert);
		isEqualTo(actUser.getEmail(), expUser.getEmail(),
				"Email пользователя '%s' не совпадает с ожидаемым '%s'", softAssert);
	}
}
