package org.example.springexpert.service;

import lombok.RequiredArgsConstructor;
import org.example.springexpert.dto.user.request.UserSaveRequestDto;
import org.example.springexpert.dto.user.request.UserUpdateRequestDto;
import org.example.springexpert.dto.user.response.UserDetailResponseDto;
import org.example.springexpert.dto.user.response.UserSaveResponseDto;
import org.example.springexpert.dto.user.response.UserUpdateResponseDto;
import org.example.springexpert.entity.User;
import org.example.springexpert.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserSaveResponseDto saveUser(UserSaveRequestDto userSaveRequestDto) {
        User newUser = new User(userSaveRequestDto.getUsername(), userSaveRequestDto.getEmail());

        User savedUser = userRepository.save(newUser);
        return new UserSaveResponseDto(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getCreatedAt(),
                savedUser.getModifiedAt()
        );
    }

    public List<UserDetailResponseDto> getUsers() {
        List<User> userList = userRepository.findAll();

        List<UserDetailResponseDto> dtoList = new ArrayList<>();
        for (User user : userList) {
            dtoList.add(new UserDetailResponseDto(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getCreatedAt(),
                    user.getModifiedAt()
            ));
        }
        return dtoList;
    }

    public UserDetailResponseDto getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NullPointerException("User not found"));

        return new UserDetailResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    @Transactional
    public UserUpdateResponseDto updateUser(Long userId, UserUpdateRequestDto userUpdateRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NullPointerException("User not found"));

        user.update(userUpdateRequestDto.getUsername(), userUpdateRequestDto.getEmail());

        return new UserUpdateResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    @Transactional
    public void deleteUser(Long userId) {
        // TODO: 작성자 확인 로직 추가 (인증 이후 작성 예정)
        userRepository.deleteById(userId);
    }
}
