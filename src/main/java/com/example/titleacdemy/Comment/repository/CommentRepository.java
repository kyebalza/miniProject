package com.example.titleacdemy.Comment.repository;

import com.example.titleacdemy.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
