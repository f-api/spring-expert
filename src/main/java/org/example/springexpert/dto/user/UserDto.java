package org.example.springexpert.dto.user;

import lombok.Getter;

@Getter
public class UserDto {
    private final Long id;
    private final String username;
    private final String email;

    public UserDto(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
}
