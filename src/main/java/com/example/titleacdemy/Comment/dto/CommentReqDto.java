package com.example.titleacdemy.Comment.dto;

import com.example.titleacdemy.entity.Comment;
import com.example.titleacdemy.entity.Member;
import com.example.titleacdemy.entity.Post;

public class CommentReqDto {
    private String content;

    //dto를 entity로 바꿔주는 구문
    //저장을 시킬때 Comment 타입으로 저장을 시켜줘야한다. 그래서 entity로 바꿔주는 것입니다.

    public Comment toEntity(Member member, Post post){// (Member member)는 인자값(매개변수)을 받아오는 곳이다.
        return new Comment(member, post, this.content);
    }
}
