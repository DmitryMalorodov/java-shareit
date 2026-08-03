package ru.practicum.shareit.items;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import ru.practicum.shareit.booking.dto.ReqBookingDto;
import ru.practicum.shareit.item.dto.RespCommentDto;
import ru.practicum.shareit.item.dto.RespItemDto;
import ru.practicum.shareit.user.dto.RespUserDto;

import java.time.LocalDateTime;

import static ru.practicum.shareit.bookings.BookingData.*;
import static ru.practicum.shareit.constant.message.ItemValidMessages.COMMENT_ACCESS_MESSAGE;
import static ru.practicum.shareit.items.ItemData.comment;
import static ru.practicum.shareit.items.ItemData.item;
import static ru.practicum.shareit.users.UserData.user;

@DisplayName("Проверка добавления комментария к вещи")
public class CreateCommentTests extends ItemsTest {

    @Test
    void checkCreateComment() throws Exception {
        //создание юзера и вещи
        RespUserDto createdUser = createUser(user);
        RespItemDto createdItem = createItem(item, createdUser.getId());

        //создание брони вещи
        ReqBookingDto reqBody = booking.toBuilder().itemId(createdItem.getId()).build();
        createBooking(reqBody, createdUser.getId());

        LocalDateTime futureTime = LocalDateTime.now().plusWeeks(1);
        try (MockedStatic<LocalDateTime> mockedTime = Mockito.mockStatic(LocalDateTime.class, Mockito.CALLS_REAL_METHODS)) {
            //устанавливаем будущее время для системы чтобы бронирование уже завершилось
            mockedTime.when(LocalDateTime::now).thenReturn(futureTime);

            //создание и проверка комментария
            RespCommentDto createdComment = createComment(comment, createdUser.getId(), createdItem.getId());
            checkComment(createdComment, comment, createdUser);
        }
    }

    @Test
    void checkCreateCommentDuringUseItem() throws Exception {
        //создание юзера и вещи
        RespUserDto createdUser = createUser(user);
        RespItemDto createdItem = createItem(item, createdUser.getId());

        //создание брони вещи
        ReqBookingDto reqBody = booking.toBuilder().itemId(createdItem.getId()).build();
        createBooking(reqBody, createdUser.getId());

        //создание и проверка комментария
        checkValidationError(createCommentResAct(comment, createdUser.getId(), createdItem.getId()), COMMENT_ACCESS_MESSAGE);
    }

    @Test
    void checkCreateCommentWithoutBooking() throws Exception {
        //создание юзера и вещи
        RespUserDto createdUser = createUser(user);
        RespItemDto createdItem = createItem(item, createdUser.getId());

        //создание и проверка комментария
        checkValidationError(createCommentResAct(comment, createdUser.getId(), createdItem.getId()), COMMENT_ACCESS_MESSAGE);
    }
}
