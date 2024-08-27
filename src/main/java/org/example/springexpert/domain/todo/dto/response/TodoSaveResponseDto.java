package org.example.springexpert.domain.todo.dto.response;

import lombok.Getter;
import org.example.springexpert.domain.user.dto.UserDto;
import org.example.springexpert.domain.user.entity.User;

import java.time.LocalDateTime;

@Getter
public class TodoSaveResponseDto {

    private final Long id;
    private final UserDto user;
    private final String title;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public TodoSaveResponseDto(
            Long id,
            User user,
            String title,
            String contents,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt
    ) {
        this.id = id;
        this.user = new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
