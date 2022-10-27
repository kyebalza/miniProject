package com.example.titleacdemy.comment.dto;

import com.example.titleacdemy.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResDto {

    private Long id;
    private String content;
    private String author;
    private LocalDateTime createdAt;
    public CommentResDto(Comment comment){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.author = comment.getMember().getNickname();
        this.createdAt = comment.getCreatedAt();
    }
}