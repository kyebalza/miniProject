package com.example.titleacdemy.post.controller;

import com.example.titleacdemy.dto.ResponseDto;
import com.example.titleacdemy.member.security.UserDetailsImpl;
import com.example.titleacdemy.post.dto.PostRequestDto;
import com.example.titleacdemy.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/api/post")
    public ResponseDto<?> createPost(@RequestPart(required = false,value = "file") List<MultipartFile> multipartFile,
                                      @RequestPart(value = "post" ) @Valid PostRequestDto postRequestDto,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return postService.createPost(multipartFile, postRequestDto, userDetails.getMember());
    }
}
