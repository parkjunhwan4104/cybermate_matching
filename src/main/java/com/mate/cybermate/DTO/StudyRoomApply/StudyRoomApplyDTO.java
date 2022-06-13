package com.mate.cybermate.DTO.ApplyHistory;

import com.mate.cybermate.domain.StudyRoomApply;
import com.mate.cybermate.domain.Study_Room;
import com.mate.cybermate.domain.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudyRoomApplyDTO {
    private Long id;
    private String roomName;

    private String memberName;

    private String subject;

    private Long age;

    private String sex;

    private Long srId;

    private boolean accept;

    public StudyRoomApplyDTO(StudyRoomApply studyRoomApply, Member member){
        this.id= studyRoomApply.getSraId();
        this.roomName= studyRoomApply.getRoomName();
        this.memberName=member.getNickName();
        this.subject= studyRoomApply.getSubject();
        this.age=member.getAge();
        this.sex=member.getSex();
        this.srId=studyRoomApply.getStudyRoom().getSrId();
        this.accept=studyRoomApply.isAccept();
    }
}
