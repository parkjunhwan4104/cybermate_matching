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

    private String subject;

    private Long age;

    private String sex;

    private Long contentNo;

    private boolean accept;

    private boolean isAuto;

    private String introduce;

    private LocalDateTime regDate=LocalDateTime.now();



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="srId")
    private Study_Room studyRoom;


    public static StudyRoomApply createRoomApply(String roomName,String subject,Long age,String sex,Long contentNo ){
        StudyRoomApply studyRoomApply=new StudyRoomApply();
        studyRoomApply.roomName=roomName;
        studyRoomApply.subject=subject;
        studyRoomApply.age=age;
        studyRoomApply.sex=sex;
        studyRoomApply.contentNo=contentNo;
        return studyRoomApply;
    }

    public static StudyRoomApply crateAutoMatchingApply(String subject,Long age, String sex,Long contentNo,String introduce,boolean isAuto){
        StudyRoomApply studyRoomApply=new StudyRoomApply();
        studyRoomApply.subject=subject;
        studyRoomApply.age=age;
        studyRoomApply.sex=sex;
        studyRoomApply.contentNo=contentNo;
        studyRoomApply.introduce=introduce;
        studyRoomApply.isAuto=isAuto;

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

    public void setIsAuto(boolean isAuto){
        this.isAuto=isAuto;
    }

    public void setAge(Long age){
        this.age=age;
    }
    public void setSex(String sex){
        this.sex=sex;
    }

    public void setRoomName(String roomName){
        this.roomName=roomName;
    }






}