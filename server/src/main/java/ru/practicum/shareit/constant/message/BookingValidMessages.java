package ru.practicum.shareit.constant.message;

public interface BookingValidMessages {
    String BOOKING_NOT_FOUND_MESSAGE = "Бронирование не найдено с id: %d";
    String BOOKING_ACCESS_ERROR = "Бронироваие может просматривать либо автор бронирования," +
            " либо владелец вещи данного бронирования";
    String BOOKING_APPROVED_ACCESS_ERROR = "Статус бронирования может менять только владелец " +
            "вещи данного бронирования";
    String USER_DO_NOT_HAVE_ANY_ITEM = "У пользователя нет ни одной вещи!";
}