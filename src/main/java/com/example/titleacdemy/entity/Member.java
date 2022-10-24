package com.example.titleacdemy.entity;

import com.example.titleacdemy.member.dto.MemberRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    public Member(String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public Member(MemberRequestDto memberRequestDto) {
        this.nickname = memberRequestDto.getNickname();
        this.email = memberRequestDto.getEmail();
        this.password = memberRequestDto.getPassword();
    }

//    //작성자만 수정, 삭제할 수 있도록 확인
    public void checkMember(Post post)  {
        if (!this.getEmail().equals(post.getMember().getEmail())) {
        };
    }
}
