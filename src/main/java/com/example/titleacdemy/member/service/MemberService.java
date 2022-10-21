package com.example.titleacdemy.member.service;

import com.example.titleacdemy.dto.ResponseDto;
import com.example.titleacdemy.entity.Member;
import com.example.titleacdemy.exception.MemberNicknameAlreadyException;
import com.example.titleacdemy.member.dto.MemberRequestDto;
import com.example.titleacdemy.member.dto.MemberResponseDto;
import com.example.titleacdemy.member.jwt.JwtUtil;
import com.example.titleacdemy.member.repository.MemberRepository;
import com.example.titleacdemy.member.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    public final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    //회원가입
    @Transactional
    public ResponseDto<?> signup(MemberRequestDto memberRequestDto) {
        // nickname 중복 검사
        if(memberRepository.findByNickname(memberRequestDto.getNickname()).isPresent()){
            throw new MemberNicknameAlreadyException();
        }
        // 패스워드 암호화로 만들기
        memberRequestDto.setEncodePwd(passwordEncoder.encode(memberRequestDto.getPassword()));

        // 닉네임과 패스워드 객체에 담시
        Member member = new Member(memberRequestDto);

        // DB에 객체 저장
        memberRepository.save(member);
        return ResponseDto.success(
                MemberResponseDto.builder()
                        .id(member.getId())
                        .nickname(member.getNickname())
                        .email(member.getEmail())
                        .createdAt(member.getCreatedAt())
                        .modifiedAt(member.getModifiedAt())
                        .build()
        );
    }
}
