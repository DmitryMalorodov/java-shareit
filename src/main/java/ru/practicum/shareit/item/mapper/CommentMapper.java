package ru.practicum.shareit.item.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.dto.ReqCommentDto;
import ru.practicum.shareit.item.dto.RespCommentDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentMapper {

    public static Comment toComment(ReqCommentDto reqCommentDto, Item item, User user) {
        return Comment.builder()
                .text(reqCommentDto.getText())
                .item(item)
                .author(user)
                .build();
    }

    public static RespCommentDto toRespCommentDto(Comment comment) {
        return RespCommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .authorName(comment.getAuthor().getName())
                .created(comment.getCreated())
                .build();
    }

    public static Collection<RespCommentDto> toRespCommentDto(Collection<Comment> comments) {
        return comments.stream()
                .map(CommentMapper::toRespCommentDto)
                .toList();
    }
}
