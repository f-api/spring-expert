package org.example.springexpert.dto.todo.response;

import lombok.Getter;
import org.example.springexpert.dto.user.UserDto;
import org.example.springexpert.entity.User;

import java.time.LocalDateTime;

@Getter
public class TodoDetailResponseDto {

    private final Long id;
    private final UserDto user;
    private final String title;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public TodoDetailResponseDto(
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
