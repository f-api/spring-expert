package org.example.springexpert.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.springexpert.config.JwtUtil;
import org.example.springexpert.config.PasswordEncoder;
import org.example.springexpert.domain.user.dto.request.UserSaveRequestDto;
import org.example.springexpert.domain.user.dto.request.UserUpdateRequestDto;
import org.example.springexpert.domain.user.dto.response.UserDetailResponseDto;
import org.example.springexpert.domain.user.dto.response.UserSaveResponseDto;
import org.example.springexpert.domain.user.dto.response.UserUpdateResponseDto;
import org.example.springexpert.domain.user.entity.User;
import org.example.springexpert.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public UserSaveResponseDto saveUser(UserSaveRequestDto userSaveRequestDto) {
        if (userRepository.existsByEmail(userSaveRequestDto.getEmail())) {
            throw new IllegalArgumentException("중복된 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(userSaveRequestDto.getPassword());

        User newUser = new User(
                userSaveRequestDto.getUsername(),
                userSaveRequestDto.getEmail(),
                encodedPassword
        );

        User savedUser = userRepository.save(newUser);

        String bearerToken = jwtUtil.createToken(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail()
        );

        return new UserSaveResponseDto(bearerToken);
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
