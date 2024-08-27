package org.example.springexpert.domain.comment.dto.response;

import lombok.Getter;
import org.example.springexpert.domain.user.dto.UserDto;
import org.example.springexpert.domain.user.entity.User;

import java.time.LocalDateTime;

@Getter
public class CommentUpdateResponseDto {

    private final Long id;
    private final UserDto user;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public CommentUpdateResponseDto(Long id, User user, String contents, LocalDateTime createdAt, LocalDateTime modifiedAt) {
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
