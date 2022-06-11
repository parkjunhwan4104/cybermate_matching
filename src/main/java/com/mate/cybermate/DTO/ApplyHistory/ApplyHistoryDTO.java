package com.mate.cybermate.DTO.ApplyHistory;

import com.mate.cybermate.domain.ApplyHistory;
import com.mate.cybermate.domain.Member;
import com.mate.cybermate.domain.Study_Room;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ApplyHistoryDTO {
    private Long applyHistoryId;

    private String roomName;

    private String memberName;

    private String subject;

    private Long age;

    private String sex;

    private Long srId;
    private Long memberId;
    private Long sraId;

    private boolean belong;

    private String requirement;

    private Long maxNum;

    private LocalDateTime regDate;

    private Long currentNum;

    private String people;

    public void setBelong(boolean belong){
        this.belong=belong;
    }

    public ApplyHistoryDTO(ApplyHistory applyHistory){
        this.applyHistoryId=applyHistory.getApplyHistoryId();
        this.roomName=applyHistory.getRoomName();
        this.memberName=applyHistory.getMember().getNickName();
        this.subject=applyHistory.getSubject();
        this.age=applyHistory.getMember().getAge();
        this.sex=applyHistory.getMember().getSex();
        this.sraId=applyHistory.getStudyRoomApply().getSraId();
        this.memberId=applyHistory.getMember().getMemberId();
        this.srId=applyHistory.getStudyRoom().getSrId();
        this.belong=applyHistory.isBelong();
        this.requirement=applyHistory.getStudyRoom().getRequirement();
        this.maxNum=applyHistory.getStudyRoom().getMaxNum();
        this.currentNum=applyHistory.getStudyRoom().getCurrentNum();
        this.people=currentNum+"/"+maxNum;

    }
}
