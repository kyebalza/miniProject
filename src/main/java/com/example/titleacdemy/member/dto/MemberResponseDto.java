package com.example.titleacdemy.member.dto;

import com.example.titleacdemy.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberResponseDto {

    private Long id;
    private String nickname;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    //@AllArgsConstructor 가 있기 때문에 주석
//    public MemberResponseDto(Member member){
//        this.id = member.getId();
//        this.createAt = member.getCreatedAt();
//        this.modifiedAt = member.getModifiedAt();
//    }
}
