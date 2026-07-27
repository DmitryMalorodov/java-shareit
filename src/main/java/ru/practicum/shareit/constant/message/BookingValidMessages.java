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
}