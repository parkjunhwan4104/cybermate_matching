package com.mate.cybermate.DTO.StudyRoomApply;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AutoMatchingApplySaveForm {

    @NotNull(message = "소개문구를 입력해주세요")
    private String introduce;

    @NotNull(message = "강의명을 입력해주세요")
    private String subject;

    @NotNull(message = "목차개수를 입력해주세요")
    private Long lectureNo;

}
