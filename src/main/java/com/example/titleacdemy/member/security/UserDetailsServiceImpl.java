package com.example.titleacdemy.member.security;

import com.example.titleacdemy.entity.Member;
import com.example.titleacdemy.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {

        // 이메일 존재여부 확인
        Member member = memberRepository.findByNickname(nickname).orElseThrow(
                () -> new RuntimeException("Not Found Account")
        );

        // 찾은 이메일을 userDetails에 객체로 저장
        UserDetailsImpl memberDetails = new UserDetailsImpl();
        memberDetails.setUser(member);

        return memberDetails;
    }
}
