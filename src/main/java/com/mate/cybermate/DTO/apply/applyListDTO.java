package com.mate.cybermate.DTO.apply;

import com.mate.cybermate.domain.MatchingApply;
import com.mate.cybermate.domain.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class applyListDTO {


    private Long id;
    private String roomName;
    private String subject;
    private String requirement;

    private Long maxNum;

    private LocalDateTime regDate;

    private Long currentNum;

    private String people;

    private Member member;



    public applyListDTO(MatchingApply matchingApply){
        this.id= matchingApply.getApplyId();
        this.subject=matchingApply.getSubject();
        this.roomName=matchingApply.getRoomName();
        this.requirement= matchingApply.getRequirement();
        this.maxNum=matchingApply.getMaxNum();
        this.currentNum=Long.valueOf(matchingApply.getMember().getStudyRoom().getStudyRoomMember().size());
        this.people=currentNum+"/"+maxNum;
        this.regDate=matchingApply.getRegDate();
        this.member=matchingApply.getMember();




    }


}
