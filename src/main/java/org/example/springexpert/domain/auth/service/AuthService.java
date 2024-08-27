package org.example.springexpert.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.example.springexpert.config.JwtUtil;
import org.example.springexpert.config.PasswordEncoder;
import org.example.springexpert.domain.auth.dto.request.SigninRequestDto;
import org.example.springexpert.domain.auth.dto.request.SignupRequestDto;
import org.example.springexpert.domain.auth.dto.response.SigninResponseDto;
import org.example.springexpert.domain.auth.dto.response.SignupResponseDto;
import org.example.springexpert.domain.auth.exception.AuthException;
import org.example.springexpert.domain.user.entity.User;
import org.example.springexpert.domain.user.enums.UserRole;
import org.example.springexpert.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
        if (userRepository.existsByEmail(signupRequestDto.getEmail())) {
            throw new IllegalArgumentException("중복된 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(signupRequestDto.getPassword());

        UserRole userRole = convertToUserRole(signupRequestDto.getUserRole());

        User newUser = new User(
                signupRequestDto.getUsername(),
                signupRequestDto.getEmail(),
                encodedPassword,
                userRole
        );

        User savedUser = userRepository.save(newUser);

        String bearerToken = jwtUtil.createToken(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getUserRole()
        );

        return new SignupResponseDto(bearerToken);
    }

    private UserRole convertToUserRole(String userRoleString) {
        try {
            return UserRole.valueOf(userRoleString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 역할 정보입니다: " + userRoleString);
        }
    }

    public SigninResponseDto signin(SigninRequestDto signinRequestDto) {
        User user = userRepository.findByEmail(signinRequestDto.getEmail())
                .orElseThrow(() -> new AuthException("가입되지 않은 이메일입니다."));

        if (!passwordEncoder.matches(signinRequestDto.getPassword(), user.getPassword())) {
            throw new AuthException("잘못된 비밀번호입니다.");
        }

        String bearerToken = jwtUtil.createToken(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getUserRole()
        );

        return new SigninResponseDto(bearerToken);
    }
}
