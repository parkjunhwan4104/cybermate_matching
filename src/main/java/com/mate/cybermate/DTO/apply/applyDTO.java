package com.mate.cybermate.DTO.apply;

import com.mate.cybermate.domain.MatchingApply;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class applyDTO {

    private Long id;
    private Long age;
    private String sex;
    private String subject;
    private String lectureName;
    private Long lectureContentNo;

    private LocalDateTime regDate;

    private String memberNickName;
    private String memberLoginId;

    private Long boardId;
    private String boardName;

    public applyDTO(MatchingApply matchingApply){
        this.id=matchingApply.getApplyId();
        this.age= matchingApply.getAge();;
        this.sex= matchingApply.getSex();
        this.subject= matchingApply.getSubject();
        this.lectureName= matchingApply.getLectureName();
        this.lectureContentNo=matchingApply.getLectureContentNo();
        this.regDate=matchingApply.getRegDate();
        this.memberNickName=matchingApply.getMember().getNickName();
        this.memberLoginId=matchingApply.getMember().getLoginId();
        this.boardId=matchingApply.getBoard().getBoardId();
        this.boardName=matchingApply.getBoard().getBoardName();
    }


}
