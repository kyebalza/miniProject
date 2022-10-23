package com.example.titleacdemy.Comment.dto;

import com.example.titleacdemy.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResDto {

    private  Long id;

    private String content;

    private String author;

    private LocalDateTime createdAt;

    public CommentResDto(Comment comment){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.author = comment.getMember().getNickname(); //이건 문제가 발생할 수 있음!!
        this.createdAt = comment.getCreatedAt();
    }
}
