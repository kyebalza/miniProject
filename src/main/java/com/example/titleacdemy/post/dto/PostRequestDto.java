package com.example.titleacdemy.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {

    @NotNull
    //@Size(min = 2, max = 10)
    private String title;

    @NotNull
    //@Size(min = 10, max = 50)
    private String content;
}
