package com.mate.cybermate.DTO.apply;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class applySaveForm {

    @NotNull(message = "나이를 입력해주세요.")
    private Long age;

    @NotBlank(message = "성별을 입력해주세요.")
    private String sex;

    @NotBlank(message = "과목명을 입력해주세요.")
    private String subject;

    @NotBlank(message = "강의명을 입력해주세요.")
    private String lectureName;

    @NotNull(message = "강의 개수를 입력해주세요.")
    private Long lectureContentNo;
}
