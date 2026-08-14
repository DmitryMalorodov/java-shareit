package ru.practicum.shareit.constant.message;

public class ItemValidMessages {
    public static final String ITEM_NOT_FOUND_MESSAGE = "Вещь не найдена с id: %d";
    public static final String ITEM_UPDATE_ACCESS_MESSAGE = "Вещь может редактировать только ее собственник";
    public static final String COMMENT_ACCESS_MESSAGE = "Комментарий может оставлять только пользователь," +
            " который брал вещь в аренду и только после окончания аренды!";
}
