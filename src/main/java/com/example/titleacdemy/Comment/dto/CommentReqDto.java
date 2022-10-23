package com.example.titleacdemy.Comment.dto;

import com.example.titleacdemy.entity.Comment;
import com.example.titleacdemy.entity.Member;
import com.example.titleacdemy.entity.Post;
import lombok.Data;

@Data
public class CommentReqDto {
    private String content;
    public Comment toEntity(Member member, Post post) {
        return new Comment(member, post, this.content);
    }


}
