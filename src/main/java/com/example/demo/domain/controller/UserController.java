package com.example.demo.domain.controller;

// ❗️아래 줄 위에 아무것도 있으면 안 됩니다!!! (예: 주석, 공백, 문자, 다른 코드)
import com.example.demo.domain.dto.SignupRequest;
import com.example.demo.domain.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<String> signup(@RequestBody SignupRequest request) {
        authService.signup(request);  // ✅ request 전체 전달
        return ResponseEntity.ok("회원가입 성공!");
    }
}
