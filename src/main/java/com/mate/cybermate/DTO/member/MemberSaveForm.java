package com.mate.cybermate.DTO.member;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class MemberSaveForm {

    @NotBlank(message = "아이디를 입력해 주세요")
    private String loginId;

    @NotBlank(message = "비밀번호를 입력해 주세요")
    private String loginPw;

    @NotBlank(message = "닉네임을 입력해 주세요")
    private String nickName;

    @NotBlank(message = "성별을 입력해 주세요")
    private String sex;

    @NotNull(message = "나이를 입력해 주세요")
    private Long age;

    @NotBlank(message = "관심분야를 입력해 주세요")
    private String favorite;

}
