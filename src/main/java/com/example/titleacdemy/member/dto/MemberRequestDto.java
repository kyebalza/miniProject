package com.example.titleacdemy.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDto {

    @NotBlank
    private String nickname;

    @NotBlank
    //@Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$", message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank
//    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}$", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 모두 사용하세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&]{8,16}$", message = "비밀번호는 8~16자 영문 대 소문자, 숫자를 사용해주세요.")
    private String password;

//    public MemberRequestDto(String nickname, String email, String password){
//        this.nickname = nickname;
//        this.email = email;
//        this.password = password;
//    }

    public void setEncodePwd(String encodePwd){
        this.password = encodePwd;
    }


}
