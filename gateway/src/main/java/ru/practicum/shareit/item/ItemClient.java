package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.item.dto.ReqCommentDto;
import ru.practicum.shareit.item.dto.ReqItemDto;

import java.util.Map;

@Service
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> getItemById(Long itemId, Long userId) {
        return get("/" + itemId, userId);
    }

    public ResponseEntity<Object> getUserItems(Long userId) {
        return get("", userId);
    }

    public ResponseEntity<Object> createItem(ReqItemDto reqItemDto, Long userId) {
        return post("", userId, reqItemDto);
    }

    public ResponseEntity<Object> updateItem(ReqItemDto reqItemDto, Long itemId, Long userId) {
        return patch("/" + itemId, userId, reqItemDto);
    }

    public ResponseEntity<Object> searchItem(String text, Long userId) {
        return get("/search?text={text}", userId, Map.of("text", text));
    }

    public ResponseEntity<Object> createComment(ReqCommentDto reqCommentDto, Long itemId, Long userId) {
        return post("/" + itemId + "/comment", userId, reqCommentDto);
    }
}
