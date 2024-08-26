package org.example.springexpert.controller;

import lombok.RequiredArgsConstructor;
import org.example.springexpert.dto.user.request.UserSaveRequestDto;
import org.example.springexpert.dto.user.request.UserUpdateRequestDto;
import org.example.springexpert.dto.user.response.UserDetailResponseDto;
import org.example.springexpert.dto.user.response.UserSaveResponseDto;
import org.example.springexpert.dto.user.response.UserUpdateResponseDto;
import org.example.springexpert.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public UserSaveResponseDto saveUser(@RequestBody UserSaveRequestDto userSaveRequestDto) {
        return userService.saveUser(userSaveRequestDto);
    }

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
