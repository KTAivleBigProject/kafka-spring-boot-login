package com.example.demo.domain.service;

import com.example.demo.domain.dto.LoginRequest;
import com.example.demo.domain.dto.LoginResponse;
import com.example.demo.domain.dto.SignupRequest;
import com.example.demo.domain.repository.RefreshTokenRepository;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.domain.user.RefreshToken;
import com.example.demo.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    // ✅ 회원가입
    public void signup(String email, String password, String name) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("이미 가입된 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(password);

        User user = User.builder()
                .email(email)
                .password(encodedPassword)
                .name(name)
                .build();

        userRepository.save(user);
    }

    // ✅ 로그인 → Access + Refresh 발급
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        // 기존 refreshToken 있으면 덮어쓰기
        RefreshToken token = RefreshToken.builder()
                .email(user.getEmail())
                .token(refreshToken)
                .build();

        refreshTokenRepository.save(token);

        return new LoginResponse(accessToken, refreshToken);
    }

    // ✅ 토큰 재발급
    public String refresh(String refreshToken) {
        String email = jwtService.extractEmail(refreshToken);

        // DB에 저장된 refreshToken과 비교
        RefreshToken saved = refreshTokenRepository.findById(email)
                .orElseThrow(() -> new RuntimeException("Refresh Token이 존재하지 않습니다."));

        if (!saved.getToken().equals(refreshToken)) {
            throw new RuntimeException("Refresh Token이 일치하지 않습니다.");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자 정보가 없습니다."));

        return jwtService.generateToken(user); // 새로운 Access Token 반환
    }

    // ✅ 로그아웃 → Refresh Token 삭제
    public void logout(String email) {
        refreshTokenRepository.deleteById(email);
    }
}
