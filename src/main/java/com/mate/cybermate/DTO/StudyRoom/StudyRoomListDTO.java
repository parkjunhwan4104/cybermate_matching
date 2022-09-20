package com.mate.cybermate.DTO.StudyRoom;

import com.mate.cybermate.CybermateApplication;
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

    private String ownerName;

    private Long maxNum;

    private LocalDateTime regDate;

    private Long currentNum;

    private String people;

    private boolean belong;

    private List<StudyRoomApply> sraList;

    private String isPermitAuto;

    public void setBelong(boolean belong){
        this.belong=belong;
    }


    public StudyRoomListDTO(Study_Room studyRoom){


        this.id= studyRoom.getSrId();
        this.subject= studyRoom.getSubject();
        this.roomName= studyRoom.getRoomName();
        this.requirement= studyRoom.getRequirement();
        this.ownerName=studyRoom.getMember().getNickName();
        this.maxNum= studyRoom.getMaxNum();
        this.currentNum= studyRoom.getCurrentNum();
        this.people=currentNum+"/"+maxNum;
        this.regDate= studyRoom.getRegDate();
        this.sraList=studyRoom.getStudyRoomApply();


        if(studyRoom.getIsPermitAuto().equals("true")){
            this.isPermitAuto="O";
        }
        else{
            this.isPermitAuto="X";
        }





    }





}
