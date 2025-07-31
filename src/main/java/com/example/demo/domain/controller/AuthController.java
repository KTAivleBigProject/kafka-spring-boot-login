package com.example.demo.domain.controller;

import com.example.demo.domain.dto.LoginRequest;
import com.example.demo.domain.dto.LoginResponse;
import com.example.demo.domain.dto.RefreshRequest;
import com.example.demo.domain.dto.LogoutRequest;
import com.example.demo.domain.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// ✅ 로그인, 토큰 재발급, 로그아웃을 처리하는 인증 컨트롤러
@RestController
@RequestMapping("/api/auth") // ✅ 정의서 기준 인증 관련 엔드포인트
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // ✅ 로그인 API: POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    // ✅ 토큰 재발급 API: POST /api/auth/refresh
    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(@RequestBody RefreshRequest request) {
        String newAccessToken = authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(newAccessToken);
    }

    // ✅ 로그아웃 API: POST /api/auth/logout
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody LogoutRequest request) {
        authService.logout(request.getEmail());
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }
}
