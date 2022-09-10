package com.mate.cybermate.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class StudyRoomApply {

    @Id
    @Column(name="sraId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sraId;

    private String roomName;

    private Long maxNum;

    private String requirement;

    private String description;

    private String subject;

    private Long age;

    private String sex;

    private Long contentNo;

    private boolean accept;

    private boolean isPermitAuto;

    private LocalDateTime regDate=LocalDateTime.now();



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="srId")
    private Study_Room studyRoom;


    public static StudyRoomApply createRoomApplyForOwner(String roomName,Long maxNum,String requirement,String description,String subject,Long con,boolean isPermit){
        StudyRoomApply studyRoomApply=new StudyRoomApply();
        studyRoomApply.roomName=roomName;
        studyRoomApply.maxNum=maxNum;
        studyRoomApply.requirement=requirement;
        studyRoomApply.description=description;
        studyRoomApply.subject=subject;
        studyRoomApply.contentNo=con;
        studyRoomApply.isPermitAuto=isPermit;


        return studyRoomApply;
    }

    public static StudyRoomApply createRoomApply(String roomName,String subject,Long age,String sex,Long contentNo ){
        StudyRoomApply studyRoomApply=new StudyRoomApply();
        studyRoomApply.roomName=roomName;
        studyRoomApply.subject=subject;
        studyRoomApply.age=age;
        studyRoomApply.sex=sex;
        studyRoomApply.contentNo=contentNo;
        return studyRoomApply;
    }


    public void setMember(Member member){
        this.member=member;
    }

    public void setStudyRoom(Study_Room studyRoom){
        this.studyRoom=studyRoom;
    }

    public void setAccept(boolean accept){
        this.accept=accept;
    }

    public void setAge(Long age){
        this.age=age;
    }
    public void setSex(String sex){
        this.sex=sex;
    }




}
