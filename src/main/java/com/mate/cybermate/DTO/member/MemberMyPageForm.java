package com.mate.cybermate.DTO.member;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class MemberMyPageForm {

    @NotBlank(message = "자기소개를 입력해 주세요")
    private String introduce;

    @NotBlank(message = "관심분야를 입력해 주세요")
    private String favorite;

    @NotBlank(message = "셩별을 입력해주세요")
    private String sex;


    @NotNull(message = "나이를 입력해 주세요")
    private Long age;
}
