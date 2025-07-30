package com.example.demo.domain.controller;

import com.example.demo.domain.dto.PostRequest;
import com.example.demo.domain.security.UserPrincipal;
import com.example.demo.domain.service.PostService;
import com.example.demo.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody PostRequest request,
                                             @AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userPrincipal.getUser(); // 토큰에서 유저 꺼냄
        postService.createPost(request, user);
        return ResponseEntity.ok("글이 등록되었습니다!");
    }
}
