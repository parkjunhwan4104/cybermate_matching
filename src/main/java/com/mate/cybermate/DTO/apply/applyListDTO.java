package com.mate.cybermate.DTO.apply;

import com.mate.cybermate.domain.MatchingApply;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class applyListDTO {


    private Long id;
    private String subject;
    private String lectureName;
    private LocalDateTime regDate;

    private String memberNickName;

    public applyListDTO(MatchingApply matchingApply){
        this.id= matchingApply.getApplyId();
        this.subject=matchingApply.getSubject();
        this.lectureName=matchingApply.getLectureName();
        this.regDate=matchingApply.getRegDate();
        this.memberNickName=matchingApply.getMember().getNickName();
    }


}
