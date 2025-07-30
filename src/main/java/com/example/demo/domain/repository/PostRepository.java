package com.example.demo.domain.repository;

import com.example.demo.domain.user.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
