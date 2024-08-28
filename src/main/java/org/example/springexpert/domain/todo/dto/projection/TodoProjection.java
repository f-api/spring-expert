package org.example.springexpert.domain.todo.dto.projection;

import lombok.Getter;
import org.example.springexpert.domain.user.entity.User;

import java.time.LocalDateTime;

@Getter
public class TodoProjection {
    private final Long id;
    private final User user;
    private final String title;
    private final String contents;
    private final String weather;
    private final int commentCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public TodoProjection(Long id, User user, String title, String contents, String weather, int commentCount, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.contents = contents;
        this.weather = weather;
        this.commentCount = commentCount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
