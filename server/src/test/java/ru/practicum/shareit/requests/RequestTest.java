package ru.practicum.shareit.requests;

import com.fasterxml.jackson.core.type.TypeReference;
import org.assertj.core.api.SoftAssertions;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.practicum.shareit.ShareItTests;
import ru.practicum.shareit.item.dto.RespItemDto;
import ru.practicum.shareit.request.dto.ReqItemRequestDto;
import ru.practicum.shareit.request.dto.RespGetItemRequestsDto;
import ru.practicum.shareit.request.dto.RespItemRequestDto;
import ru.practicum.shareit.request.dto.ResponseDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.GeneralAssertions.*;

public class RequestTest extends ShareItTests {
    static final String REQUESTS = "/requests";
    static final String REQUESTS_ALL = "/requests/all";
    static final String REQUESTS_ID = "/requests/{requestId}";

    protected ResultActions createRequestResAct(ReqItemRequestDto request, Long userId) throws Exception {
        return mockMvc.perform(post(REQUESTS)
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-Sharer-User-Id", userId)
                .content(objectMapper.writeValueAsString(request)));
    }

    protected RespItemRequestDto createRequest(ReqItemRequestDto request, Long userId) throws Exception {
        String jsonResponse = createRequestResAct(request, userId)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(jsonResponse, RespItemRequestDto.class);
    }

    protected RespGetItemRequestsDto getRequestById(Long requestId, Long userId) throws Exception {
        String jsonResponse = mockMvc.perform(get(REQUESTS_ID, requestId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(jsonResponse, RespGetItemRequestsDto.class);
    }

    protected List<RespItemRequestDto> getItemRequests(Long userId) throws Exception {
        String jsonResponse = mockMvc.perform(get(REQUESTS_ALL)
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(jsonResponse, new TypeReference<>() {});
    }

    protected List<RespGetItemRequestsDto> getUserItemRequests(Long userId) throws Exception {
        String jsonResponse = mockMvc.perform(get(REQUESTS)
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(jsonResponse, new TypeReference<>() {});
    }

    protected void checkRequest(RespItemRequestDto actRequest, ReqItemRequestDto expData, Long expRequestorId) {
        SoftAssertions softAssert = new SoftAssertions();

        isNotNull(actRequest.getId(), "ID запроса '%d' отсутствует", softAssert);
        isEqualTo(actRequest.getDescription(), expData.getDescription(),
                "Текст запроса '%s' не соответствует ожидаемому '%s'", softAssert);
        isEqualTo(actRequest.getRequestorId(), expRequestorId,
                "ID создателя запроса '%d' не соответствует ожидаемому '%d'", softAssert);
        isCloseTo(actRequest.getCreated(), LocalDateTime.now(),
                "Дата/Время создания запроса '%s' не соответствует ожидаемому '%s'", softAssert);

        softAssert.assertAll();
    }

    protected void checkRequest(RespGetItemRequestsDto actRequest, ReqItemRequestDto expData, List<RespItemDto> expResponses) {
        SoftAssertions softAssert = new SoftAssertions();

        isNotNull(actRequest.getId(), "ID запроса '%d' отсутствует", softAssert);
        isEqualTo(actRequest.getDescription(), expData.getDescription(),
                "Текст запроса '%s' не соответствует ожидаемому '%s'", softAssert);
        isCloseTo(actRequest.getCreated(), LocalDateTime.now(),
                "Дата/Время создания запроса '%s' не соответствует ожидаемому '%s'", softAssert);
        isEqualTo(actRequest.getItems().size(), expResponses.size(),
                "Количество ответов на запрос '%d' не соответствует ожидаемому '%d'", softAssert);

        Map<Long, ResponseDto> actResponses = actRequest.getItems().stream()
                .collect(Collectors.toMap(ResponseDto::getId, Function.identity()));
        expResponses.forEach(expResponse -> {
            ResponseDto actResponse = actResponses.get(expResponse.getId());
            checkResponse(actResponse, expResponse, softAssert);
        });

        softAssert.assertAll();
    }

    private void checkResponse(ResponseDto actResponse, RespItemDto expResponse, SoftAssertions softAssert) {
        isEqualTo(actResponse.getId(), expResponse.getId(),
                "ID ответа на запрос '%d' не соответствует ожидаемому '%d'", softAssert);
        isEqualTo(actResponse.getName(), expResponse.getName(),
                "Имя вещи ответа на запрос '%s' не соответствует ожидаемому '%s'", softAssert);
        isEqualTo(actResponse.getOwnerId(), expResponse.getOwner().getId(),
                "ID юзера, который ответил на запрос '%d' не соответствует ожидаемому '%d'", softAssert);
    }
}
