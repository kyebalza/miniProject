package com.example.titleacdemy.post.dto;

import com.example.titleacdemy.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {

    private Long Id;
    private String title;
    private String content;
    private String imgUrl;

    public PostResponseDto(Post post){
        this.Id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.imgUrl = post.getImgUrl();
    }
}
