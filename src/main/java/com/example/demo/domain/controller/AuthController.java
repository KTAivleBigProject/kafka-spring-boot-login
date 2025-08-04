package com.example.demo.domain.controller;

import com.example.demo.domain.dto.LoginRequest;
import com.example.demo.domain.dto.LoginResponse;
import com.example.demo.domain.dto.RefreshRequest;
import com.example.demo.domain.dto.LogoutRequest;
import com.example.demo.domain.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(@RequestBody RefreshRequest request) {
        String newAccessToken = authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(newAccessToken);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody LogoutRequest request) {
        authService.logout(request.getEmail());
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> withdraw(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        authService.withdraw(email); // ✅ email 기반 삭제 메서드 호출
        return ResponseEntity.ok().build();
    }
}   
