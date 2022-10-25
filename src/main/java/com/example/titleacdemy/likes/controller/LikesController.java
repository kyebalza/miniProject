package com.example.titleacdemy.likes.controller;

import com.example.titleacdemy.dto.ResponseDto;
import com.example.titleacdemy.likes.dto.likeRequestDto;
import com.example.titleacdemy.likes.service.LikesService;
import com.example.titleacdemy.member.dto.MemberRequestDto;
import com.example.titleacdemy.member.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

//    @PostMapping("/post/{postId}/like")
//    public ResponseDto<?> likeUp(@PathVariable Long postId,
//                                 @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
//        return likesService.likeUp(postId, userDetailsImpl.getMember().getId());
//    }
    @PostMapping("/post/like")
    public ResponseDto<?> likeUp(@RequestBody @Valid likeRequestDto likeRequestDto,
                                 @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return likesService.likeUp(likeRequestDto, userDetailsImpl.getMember().getId());
    }
}
