package com.example.titleacdemy.post.repository;

import com.example.titleacdemy.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
