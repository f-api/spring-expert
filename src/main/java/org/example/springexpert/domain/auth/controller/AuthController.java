package org.example.springexpert.domain.auth.controller;

import lombok.RequiredArgsConstructor;
import org.example.springexpert.domain.auth.dto.request.SigninRequestDto;
import org.example.springexpert.domain.auth.dto.request.SignupRequestDto;
import org.example.springexpert.domain.auth.dto.response.SigninResponseDto;
import org.example.springexpert.domain.auth.dto.response.SignupResponseDto;
import org.example.springexpert.domain.auth.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/signup")
    public SignupResponseDto signup(@RequestBody SignupRequestDto signupRequestDto) {
        return authService.signup(signupRequestDto);
    }

    @PostMapping("/auth/signin")
    public SigninResponseDto signin(@RequestBody SigninRequestDto signinRequestDto) {
        return authService.signin(signinRequestDto);
    }

}
