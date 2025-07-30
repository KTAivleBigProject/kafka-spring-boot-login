package com.example.demo.domain.service;

import com.example.demo.domain.dto.PostRequest;
import com.example.demo.domain.repository.PostRepository;
import com.example.demo.domain.user.Post;
import com.example.demo.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void createPost(PostRequest request, User user) {
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .user(user) // 토큰에서 받아온 사용자
                .build();

        postRepository.save(post);
    }
}
