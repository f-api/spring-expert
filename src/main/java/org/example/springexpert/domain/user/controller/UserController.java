package org.example.springexpert.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.springexpert.domain.user.dto.request.UserUpdateRequestDto;
import org.example.springexpert.domain.user.dto.response.UserDetailResponseDto;
import org.example.springexpert.domain.user.dto.response.UserUpdateResponseDto;
import org.example.springexpert.domain.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public List<UserDetailResponseDto> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/users/{userId}")
    public UserDetailResponseDto getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }

    @PutMapping("/users/{userId}")
    public UserUpdateResponseDto updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        return userService.updateUser(userId, userUpdateRequestDto);
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
