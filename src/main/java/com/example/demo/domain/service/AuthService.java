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

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    // ✅ 회원가입
    public void signup(SignupRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("이미 가입된 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .name(request.getName())
                .build();

        userRepository.save(user);
    }

    // ✅ 로그인
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        RefreshToken token = RefreshToken.builder()
                .userId(user.getUserId()) // userId 기준 저장
                .token(refreshToken)
                .build();

        refreshTokenRepository.save(token);

        return new LoginResponse(accessToken, refreshToken);
    }

    // ✅ 토큰 재발급
    public String refresh(String refreshToken) {
        //이 토큰이 어떤 사용자의 것인지 확인하는 과정
        Long userId = jwtService.extractUserId(refreshToken); // userId 추출

        RefreshToken saved = refreshTokenRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Refresh Token이 존재하지 않습니다."));

        if (!saved.getToken().equals(refreshToken)) {
            throw new RuntimeException("Refresh Token이 일치하지 않습니다.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자 정보가 없습니다."));

        return jwtService.generateToken(user);
    }

    // ✅ 로그아웃
    public void logout(Long userId) {
        refreshTokenRepository.deleteById(userId);
    }
}
