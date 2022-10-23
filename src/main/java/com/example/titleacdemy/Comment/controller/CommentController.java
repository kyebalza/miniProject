package com.example.titleacdemy.Comment.controller;

import com.example.titleacdemy.Comment.dto.CommentReqDto;
import com.example.titleacdemy.Comment.dto.CommentResDto;
import com.example.titleacdemy.Comment.service.CommentService;
import com.example.titleacdemy.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{post_id}/comments")
public class CommentController {
    private final CommentService commentService;
    @PostMapping
    public ResponseDto<?> create(@PathVariable("post_id") Long postId, @RequestBody CommentReqDto dto){
        return new ResponseDto<>(true,null,null);
    }
    @GetMapping
    public ResponseDto<?> getAllByPostId(@PathVariable("post_id") Long postId){
        return new ResponseDto<>(true,null,null);
    }

    @PutMapping("/{comment_id}")
    public ResponseDto<?> update(@PathVariable("post_id") Long postId,
                               @PathVariable("comment_id") Long commentId,
                               @RequestBody CommentReqDto dto) {
        return new ResponseDto<>(true, null,null);
    }

    @DeleteMapping("/{comment_id}")
    public ResponseDto<?> delete(@PathVariable("post_id") Long postId,
                               @PathVariable("comment_id") Long commentId){
        return new ResponseDto<>(true, null,null);
    }
    }

