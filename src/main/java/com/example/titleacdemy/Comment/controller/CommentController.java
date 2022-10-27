package com.example.titleacdemy.comment.controller;

import com.example.titleacdemy.comment.dto.CommentReqDto;
import com.example.titleacdemy.comment.dto.CommentResDto;
import com.example.titleacdemy.comment.service.CommentService;
import com.example.titleacdemy.dto.ResponseDto;
import com.example.titleacdemy.member.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post/{post_id}/comment")
public class CommentController {
    private final CommentService commentService;
    @PostMapping
    public ResponseDto<?> create(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                                 @PathVariable("post_id") Long postId,
                                 @RequestBody CommentReqDto dto) {

        return commentService.create(userDetailsImpl.getMember().getId(),postId, dto);
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


        return commentService.update(userDetailsImpl,postId, commentId, dto);
    }

    @DeleteMapping("/{comment_id}")
    public ResponseDto<?> delete(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                                 @PathVariable("post_id") Long postId,
                               @PathVariable("comment_id") Long commentId){


        return commentService.delete(userDetailsImpl,postId, commentId);
    }
    }
