package com.mate.cybermate.DTO.StudyRoom;

import com.mate.cybermate.domain.Member;
import com.mate.cybermate.domain.StudyRoomApply;
import com.mate.cybermate.domain.Study_Room;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class StudyRoomListDTO {

    private Long id;
    private String roomName;
    private String subject;
    private String requirement;

    private Long maxNum;

    private LocalDateTime regDate;

    private Long currentNum;

    private String people;


    private List<Member> members;

    private List<List<Member>> total;

    private boolean belong;


    public void setBelong(boolean belong){
        this.belong=belong;
    }

    public boolean getBelong(){
        return this.belong;
    }

    public StudyRoomListDTO(Study_Room studyRoom){


        this.id= studyRoom.getSrId();
        this.subject= studyRoom.getSubject();
        this.roomName= studyRoom.getRoomName();
        this.requirement= studyRoom.getRequirement();
        this.maxNum= studyRoom.getMaxNum();
        this.currentNum= studyRoom.getCurrentNum();
        this.people=currentNum+"/"+maxNum;
        this.regDate= studyRoom.getRegDate();
        this.members=studyRoom.getMemberList();
        this.belong=getBelong();
        this.total=studyRoom.getTotal();
    }





}
