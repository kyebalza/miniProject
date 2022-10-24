package com.example.titleacdemy.likes.controller;

import com.example.titleacdemy.dto.ResponseDto;
import com.example.titleacdemy.likes.service.LikesService;
import com.example.titleacdemy.member.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    @PostMapping("/post/{postId}/like")
    public ResponseDto<?> likeUp(@PathVariable Long id,
                                 @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return likesService.likeUp(id, userDetailsImpl.getMember().getId());
    }
}
