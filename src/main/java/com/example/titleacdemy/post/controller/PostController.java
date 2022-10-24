package com.example.titleacdemy.post.controller;

import com.example.titleacdemy.dto.ResponseDto;
import com.example.titleacdemy.member.security.UserDetailsImpl;
import com.example.titleacdemy.post.dto.PostRequestDto;
import com.example.titleacdemy.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/api/post")
    public ResponseDto<?> createPost(@RequestPart(required = false,value = "file") MultipartFile multipartFile,
                                      @RequestPart(value = "post" ) @Valid PostRequestDto postRequestDto,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return postService.createPost(multipartFile, postRequestDto, userDetails.getMember());
    }

    @GetMapping("/api/post")
    public ResponseDto<?> getPostAll(){
        return postService.getPostAll();
    }

    //상세 게시글 조회
    @GetMapping("/api/post/{postId}")
    public ResponseDto<?> getPostOne(@PathVariable Long postId) {
        return postService.getPostOne(postId);
    }

    //게시글 삭제
    @DeleteMapping("/api/post/{postId}")
    public ResponseDto<?> deletePost(@PathVariable Long postId,
                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.deletePost(postId, userDetails.getMember());
    }

    //게시글 수정
    @PutMapping("/api/post/{postId}")
    public ResponseDto<?>updatePost(@PathVariable Long postId,
                                    @AuthenticationPrincipal UserDetailsImpl userDetails,
                                    @RequestBody PostRequestDto postRequestDto){
        return postService.updatePost(postId, userDetails.getMember(), postRequestDto);
    }

}


