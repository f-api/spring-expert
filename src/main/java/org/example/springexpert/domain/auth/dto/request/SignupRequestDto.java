package org.example.springexpert.domain.auth.dto.request;

import lombok.Getter;

@Getter
public class SignupRequestDto {

    private String username;
    private String email;
    private String password;
}
