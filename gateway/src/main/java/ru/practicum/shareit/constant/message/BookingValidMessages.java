package ru.practicum.shareit.constant.message;

public class BookingValidMessages {
    public static final String START_DATE_NULL_MESSAGE = "Дата начала бронирования не может быть пустой";
    public static final String END_DATE_NULL_MESSAGE = "Дата окончания бронирования не может быть пустой";
    public static final String ITEM_ID_NULL_MESSAGE = "ID вещи для бронирования не может быть пустым";
    public static final String BOOKING_NOT_FOUND_MESSAGE = "Бронирование не найдено с id: %d";
    public static final String BOOKING_ACCESS_ERROR = "Бронироваие может просматривать либо автор бронирования," +
            " либо владелец вещи данного бронирования";
    public static final String BOOKING_APPROVED_ACCESS_ERROR = "Статус бронирования может менять только владелец " +
            "вещи данного бронирования";
    public static final String BOOKING_START_DATE_VALID_MESSAGE = "Дата начала бронирования не может быть в прошлом";
    public static final String BOOKING_END_DATE_VALID_MESSAGE = "Дата окончания бронирования может быть только в будущем";
    public static final String USER_DO_NOT_HAVE_ANY_ITEM = "У пользователя нет ни одной вещи!";
    public static final String INCORRECT_STATE_PARAMETER = "Некорректный параметр state - '%s'";
}