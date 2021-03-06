package com.mate.cybermate.DTO.StudyRoomApply;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class StudyRoomApplySaveForm {



    @NotBlank(message = "스터디룸 이름을 입력해주세요.")
    private String roomName;

    @NotNull(message = "최대 인원을 입력해주세요.")
    private Long maxNum;

    @NotBlank(message = "요구조건을 입력해주세요.")
    private String requirement;

    @NotBlank(message = "소개문구를 입력해주세요.")
    private String description;

    @NotBlank(message = "과목을 입력해주세요.")
    private String subject;

    @NotNull(message = "강의 개수를 입력해주세요")
    private Long contentNo;




}
