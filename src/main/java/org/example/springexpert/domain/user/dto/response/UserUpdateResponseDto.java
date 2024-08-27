package org.example.springexpert.domain.user.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserUpdateResponseDto {

    private final Long id;
    private final String username;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public UserUpdateResponseDto(Long id, String username, String email, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
