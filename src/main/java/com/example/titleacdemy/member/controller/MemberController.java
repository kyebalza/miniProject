package com.example.titleacdemy.member.controller;

import com.example.titleacdemy.dto.ResponseDto;
import com.example.titleacdemy.member.dto.LoginRequestDto;
import com.example.titleacdemy.member.dto.MemberRequestDto;
import com.example.titleacdemy.member.jwt.JwtUtil;
import com.example.titleacdemy.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

    private final JwtUtil jwtUtil;
    private final MemberService memberService;

    //회원가입
    @PostMapping("/auth/signup")
    public ResponseDto<?> signup(@RequestBody @Valid MemberRequestDto memberRequestDto) {
        return memberService.signup(memberRequestDto);
    }

    //로그인
    @PostMapping("/auth/login")
    public ResponseDto<?> login(@RequestBody @Valid LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return memberService.login(loginRequestDto, response);
    }
}
