package ru.practicum.shareit.constant.message;

public class BookingValidMessages {
    public static final String START_DATE_NULL_MESSAGE = "Дата начала бронирования не может быть пустой";
    public static final String END_DATE_NULL_MESSAGE = "Дата окончания бронирования не может быть пустой";
    public static final String ITEM_ID_NULL_MESSAGE = "ID вещи для бронирования не может быть пустым";
    public static final String BOOKING_START_DATE_VALID_MESSAGE = "Дата начала бронирования не может быть в прошлом";
    public static final String BOOKING_END_DATE_VALID_MESSAGE = "Дата окончания бронирования может быть только в будущем";
    public static final String INCORRECT_STATE_PARAMETER = "Некорректный параметр state - '%s'";
}