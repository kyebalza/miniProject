package com.example.titleacdemy.member.dto;

import com.example.titleacdemy.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemberResponseDto {

    private Long id;

    private LocalDateTime createAt;

    private LocalDateTime modifiedAt;

    public MemberResponseDto(Member member){
        this.id = member.getId();
        this.createAt = member.getCreatedAt();
        this.modifiedAt = member.getModifiedAt();
    }
}
