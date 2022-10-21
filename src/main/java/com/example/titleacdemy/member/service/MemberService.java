package com.example.titleacdemy.member.service;

import com.example.titleacdemy.dto.ResponseDto;
import com.example.titleacdemy.entity.Member;
import com.example.titleacdemy.entity.RefreshToken;
import com.example.titleacdemy.exception.MemberNicknameAlreadyException;
import com.example.titleacdemy.exception.MemberNotFoundException;
import com.example.titleacdemy.member.dto.LoginRequestDto;
import com.example.titleacdemy.member.dto.MemberRequestDto;
import com.example.titleacdemy.member.dto.MemberResponseDto;
import com.example.titleacdemy.member.dto.TokenDto;
import com.example.titleacdemy.member.jwt.JwtUtil;
import com.example.titleacdemy.member.repository.MemberRepository;
import com.example.titleacdemy.member.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    public final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    // response에 담는 메서드
    private void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }



    //회원가입
    @Transactional
    public ResponseDto<?> signup(MemberRequestDto memberRequestDto) {
        // email 중복 검사
        if(memberRepository.findByEmail(memberRequestDto.getEmail()).isPresent()){
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

    //로그인
    @Transactional
    public ResponseDto<?> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        // 아이디(이메일) 있는지 확인
        Member member = memberRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(
                () -> new MemberNotFoundException()
        );
        // 비밀번호 있는지 확인
        if(!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())) {
            throw new MemberNotFoundException();
        }

        // 엑세스토큰, 리프레쉬토큰 생성
        TokenDto tokenDto = jwtUtil.createAllToken(loginRequestDto.getEmail());

        // 리프레쉬 토큰은 DB에서 찾기
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByMemberemail(loginRequestDto.getEmail());

        // 리프레쉬토큰 null인지 아닌지 에 따라서
        // 값을 가지고있으면 save
        // 값이 없으면 newToken 만들어내서 save
        if(refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        }else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), loginRequestDto.getEmail());
            refreshTokenRepository.save(newToken);
        }

        // 헤더에 response == 엑세스 토큰인지 리프레쉬 토큰인지
        // tokenDto == 생성된 엑세스, 리프레쉬 담기
        setHeader(response, tokenDto);

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
