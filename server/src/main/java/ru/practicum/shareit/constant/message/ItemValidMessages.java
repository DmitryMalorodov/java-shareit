package ru.practicum.shareit.constant.message;

public interface ItemValidMessages {
    String ITEM_NOT_FOUND_MESSAGE = "Вещь не найдена с id: %d";
    String ITEM_UPDATE_ACCESS_MESSAGE = "Вещь может редактировать только ее собственник";
    String COMMENT_ACCESS_MESSAGE = "Комментарий может оставлять только пользователь," +
            " который брал вещь в аренду и только после окончания аренды!";
}
