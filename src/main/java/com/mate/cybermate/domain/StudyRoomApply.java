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

    private Long currentNum;

    private String requirement;

    private String description;

    private String subject;

    private Long age;

    private String sex;

    private LocalDateTime regDate=LocalDateTime.now();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="srId")
    private Study_Room studyRoom;


    public static StudyRoomApply createRoomApplyForOwner(String roomName,Long maxNum,String requirement,String description,String subject){
        StudyRoomApply studyRoomApply=new StudyRoomApply();
        studyRoomApply.roomName=roomName;
        studyRoomApply.maxNum=maxNum;
        studyRoomApply.requirement=requirement;
        studyRoomApply.description=description;
        studyRoomApply.subject=subject;


        return studyRoomApply;
    }

    public static StudyRoomApply createRoomApply(String roomName,String subject,Long age,String sex){
        StudyRoomApply studyRoomApply=new StudyRoomApply();
        studyRoomApply.roomName=roomName;
        studyRoomApply.subject=subject;
        studyRoomApply.age=age;
        studyRoomApply.sex=sex;

        return studyRoomApply;
    }


    public void setMember(Member member){
        this.member=member;
    }

    public void setStudyRoom(Study_Room studyRoom){
        this.studyRoom=studyRoom;
    }





}
