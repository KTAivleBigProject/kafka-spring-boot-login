package com.example.demo.domain.controller;

import com.example.demo.domain.dto.LoginRequest;
import com.example.demo.domain.dto.LoginResponse;
import com.example.demo.domain.dto.SignupRequest;
import com.example.demo.domain.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // ✅ 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest request) {
        authService.signup(request.getEmail(), request.getPassword(), request.getName());
        return ResponseEntity.ok("회원가입 성공!");
    }

    // ✅ 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    // ✅ 🔄 Access Token 재발급
    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(@RequestBody RefreshRequest request) {
        String newAccessToken = authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(newAccessToken);
    }

    // ✅ ❌ 로그아웃 (Refresh Token 삭제)
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody LogoutRequest request) {
        authService.logout(request.getEmail());
        return ResponseEntity.ok("로그아웃 되었습니다.");
        
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMyInfo(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(Map.of(
            "email", userPrincipal.getUsername(),
            "name", userPrincipal.getUser().getName()
        ));
    }

}
