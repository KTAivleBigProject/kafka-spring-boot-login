package com.example.demo.domain.controller;

import com.example.demo.domain.dto.SignupRequest;
import com.example.demo.domain.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// ✅ 회원가입 전용 컨트롤러
@RestController
@RequestMapping("/api/users") // ✅ 정의서 기준 회원가입 엔드포인트
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;

    // ✅ 회원가입 API: POST /api/users
    @PostMapping
    public ResponseEntity<String> signup(@RequestBody SignupRequest request) {
        authService.signup(request.getEmail(), request.getPassword(), request.getName());
        return ResponseEntity.ok("회원가입 성공!");
    }
}
