package ru.practicum.shareit.constant.message;

import ru.practicum.shareit.constant.ValidMessages;

public class ItemValidMessages extends ValidMessages {
    public static final String DESCRIPTION_BLANK_MESSAGE = "Описание не может быть пустым";
    public static final String AVAILABLE_NULL_MESSAGE = "Статус доступности к аренде не может быть пустым";
    public static final String ITEM_NOT_FOUND_MESSAGE = "Вещь не найдена с id: %d";
    public static final String ITEM_UPDATE_ACCESS_MESSAGE = "Вещь может редактировать только ее собственник";
}
