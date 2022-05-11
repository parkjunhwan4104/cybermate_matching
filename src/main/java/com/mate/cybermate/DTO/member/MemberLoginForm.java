package com.mate.cybermate.DTO.member;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MemberLoginForm {


    @NotBlank(message = "아이디를 입력해 주세요.")
    private String loginId;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    private String loginPw;




}
