package com.example.titleacdemy.likes.dto;

import com.example.titleacdemy.entity.Likes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeResponseDto {

    private boolean likeCheck;
    private Long likeCnt;

}
