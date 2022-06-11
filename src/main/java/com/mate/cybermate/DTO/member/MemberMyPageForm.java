package com.mate.cybermate.DTO.member;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MemberMyPageForm {

    @NotBlank(message = "자기소개를 입력해 주세요")
    private String introduce;

    @NotBlank(message = "관심분야를 입력해 주세요")
    private String favorite;
}
