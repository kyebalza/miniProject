package com.example.titleacdemy.Comment.controller;

import com.example.titleacdemy.Comment.dto.CommentReqDto;
import com.example.titleacdemy.Comment.dto.CommentResDto;
import com.example.titleacdemy.Comment.service.CommentService;
import com.example.titleacdemy.dto.ResponseDto;
import com.example.titleacdemy.member.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts/{post_id}/comments")
public class CommentController {
    private final CommentService commentService;
    @PostMapping
    public ResponseDto<?> create(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,@PathVariable("post_id") Long postId, @RequestBody CommentReqDto dto) {
            commentService.create(userDetailsImpl.getMember().getId(),postId, dto);
        return new ResponseDto<>(true, null, null);
    }
    @GetMapping
    public ResponseDto<?> getAllByPostId(@PathVariable("post_id") Long postId){
        List<CommentResDto> resDtos = commentService.readAll(postId);
        return new ResponseDto<>(true,resDtos,null);
    }

    @PutMapping("/{comment_id}")
    public ResponseDto<?> update(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                                 @PathVariable("post_id") Long postId,
                               @PathVariable("comment_id") Long commentId,
                               @RequestBody CommentReqDto dto) {

        commentService.update(userDetailsImpl,postId, commentId, dto);
        return new ResponseDto<>(true, null,null);
    }

    @DeleteMapping("/{comment_id}")
    public ResponseDto<?> delete(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                                 @PathVariable("post_id") Long postId,
                               @PathVariable("comment_id") Long commentId){

        commentService.delete(userDetailsImpl,postId, commentId);
        return new ResponseDto<>(true, null,null);
    }
    }
