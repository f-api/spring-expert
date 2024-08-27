package org.example.springexpert.domain.auth.dto.request;

import lombok.Getter;

@Getter
public class SigninRequestDto {

    private String email;
    private String password;
}
