package ru.practicum.shareit.requests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.GeneralAssertions;
import ru.practicum.shareit.item.dto.ReqItemDto;
import ru.practicum.shareit.item.dto.RespItemDto;
import ru.practicum.shareit.request.dto.RespGetItemRequestsDto;
import ru.practicum.shareit.request.dto.RespItemRequestDto;
import ru.practicum.shareit.user.dto.RespUserDto;

import java.util.List;

import static ru.practicum.shareit.items.ItemData.item;
import static ru.practicum.shareit.requests.RequestData.request;
import static ru.practicum.shareit.users.UserData.user;
import static ru.practicum.shareit.users.UserData.user2;

@DisplayName("Проверка создания запроса")
public class RequestTests extends RequestTest {

    @Test
    void checkCreateRequest() throws Exception {
        RespUserDto requestOwner = createUser(user);
        RespItemRequestDto createdRequest = createRequest(request, requestOwner.getId());
        checkRequest(createdRequest, request, requestOwner.getId());
    }

    @Test
    void checkGetAllRequests() throws Exception {
        RespUserDto requestOwner = createUser(user);
        RespUserDto otherUser = createUser(user2);
        createRequest(request, requestOwner.getId());

        List<RespItemRequestDto> itemRequests = getItemRequests(otherUser.getId());
        GeneralAssertions.isTrue(itemRequests.size() == 1,
                "Размер списка запросов не соответствует ожидаемому");
    }

    @Test
    void checkGetAllRequestsExceptRequestsOfOwner() throws Exception {
        RespUserDto requestOwner = createUser(user);
        createRequest(request, requestOwner.getId());

        List<RespItemRequestDto> itemRequests = getItemRequests(requestOwner.getId());
        GeneralAssertions.isTrue(itemRequests.isEmpty(),
                "Размер списка запросов не соответствует ожидаемому");
    }

    @Test
    void checkGetAllUserRequests() throws Exception {
        RespUserDto requestOwner = createUser(user);
        createRequest(request, requestOwner.getId());

        List<RespGetItemRequestsDto> itemRequests = getUserItemRequests(requestOwner.getId());
        GeneralAssertions.isTrue(itemRequests.size() == 1,
                "Размер списка запросов не соответствует ожидаемому");
    }

    @Test
    void checkGetOnlyAllUserRequests() throws Exception {
        RespUserDto requestOwner = createUser(user);
        RespUserDto otherUser = createUser(user2);
        createRequest(request, requestOwner.getId());

        List<RespGetItemRequestsDto> itemRequests = getUserItemRequests(otherUser.getId());
        GeneralAssertions.isTrue(itemRequests.isEmpty(),
                "Размер списка запросов не соответствует ожидаемому");
    }

    @Test
    void checkGetRequestById() throws Exception {
        //создаем двух юзеров
        RespUserDto requestOwner = createUser(user);
        RespUserDto otherUser = createUser(user2);

        //создаем запрос и получаем его id
        Long requestId = getIdFromObject(createRequestResAct(request, requestOwner.getId()));

        //создаем ответ на запрос (создав вещь по id запроса)
        ReqItemDto reqItemBody = item.toBuilder().requestId(requestId).build();
        RespItemDto createdItem = createItem(reqItemBody, otherUser.getId());

        //получаем запрос по id и проверяем все поля
        RespGetItemRequestsDto actRequest = getRequestById(requestId, requestOwner.getId());
        checkRequest(actRequest, request, List.of(createdItem));
    }
}
