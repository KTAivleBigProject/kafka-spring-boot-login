package com.example.demo.domain.controller;

import com.example.demo.domain.dto.SignupRequest;
import com.example.demo.domain.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<String> signup(@jakarta.validation.Valid @RequestBody SignupRequest request) {
        authService.signup(request);
        return ResponseEntity.ok("회원가입 성공!");
    }
}
