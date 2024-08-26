package org.example.springexpert.dto.comment.response;

import lombok.Getter;
import org.example.springexpert.dto.user.UserDto;
import org.example.springexpert.entity.User;

import java.time.LocalDateTime;

@Getter
public class CommentSaveResponseDto {

    private final Long id;

    private final UserDto user;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public CommentSaveResponseDto(Long id, User user, String contents, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.user = new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
        this.contents = contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
