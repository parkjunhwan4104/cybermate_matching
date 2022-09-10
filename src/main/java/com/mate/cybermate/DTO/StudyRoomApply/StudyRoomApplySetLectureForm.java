package com.mate.cybermate.DTO.StudyRoomApply;


import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class StudyRoomApplySetLectureForm {

    @NotNull(message = "강의 개수를 입력해주세요.")
    private Long lectureNo;
}
