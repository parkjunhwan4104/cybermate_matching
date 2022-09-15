package com.mate.cybermate.DTO.member;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class MemberSaveForm {

    @NotBlank(message = "입력필요")
    private String loginId;

    @NotBlank(message = "입력필요")
    private String loginPw;

    @NotBlank(message = "입력필요")
    private String nickName;

    @NotBlank(message = "입력필요")
    private String sex;

    @NotNull(message = "입력필요")
    private Long age;

    @NotBlank(message = "입력필요")
    private String favorite;

}
