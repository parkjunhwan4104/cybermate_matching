package com.mate.cybermate.domain;
import com.mate.cybermate.CybermateApplication;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Study_Room {

    @Id
    @Column(name="srId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long srId;

    private String roomName;


    private String subject;

    private Long maxNum;

    private Long currentNum;

    private String requirement;


    private String goal;

    private Long goalLectureNo;

    private Long leftContent;

    private Long contentNo;

    private Long currentLectureNo;

    private Long matesLectureNo;

    private float lecturePercent;

    private float matesPercent;

    private LocalDateTime regDate=LocalDateTime.now();



    private static List<Member> memberList=new ArrayList<>();

    @OneToMany(mappedBy = "studyRoom" ,fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<StudyRoomApply> studyRoomApply=new ArrayList<>();




    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="boardId")
    private Board board;

    public static Study_Room createRoom(StudyRoomApply studyRoomApply){
        Study_Room studyRoom=new Study_Room();
        studyRoom.roomName=studyRoomApply.getRoomName();
        studyRoom.maxNum=studyRoomApply.getMaxNum();
        studyRoom.subject=studyRoomApply.getSubject();
        studyRoom.requirement=studyRoomApply.getRequirement();
        studyRoom.currentNum=Long.valueOf(1);
        studyRoom.contentNo=studyRoomApply.getContentNo();
        return studyRoom;
    }


    public void setBoard(Board board){
        this.board=board;
    }

    public void setGoal(String goal){
        this.goal=goal;
    }

    public void setGoalLectureNo(Long goalLectureNo){

        this.goalLectureNo=goalLectureNo;
    }

    public void setLeftContent(Long num){
        this.leftContent=num;
    }

    public void setCurrentNum(){

        this.currentNum=this.currentNum+1;
    }

    public void setCurrentLectureNo(Long currentLectureNo){


            this.currentLectureNo=currentLectureNo;

    }

    public void setMatesLectureNo(Long no){


            this.matesLectureNo = no;

    }

    public void setLecturePercent(float no){
        this.lecturePercent=no;
    }

    public void setMatesPercent(float no){
        this.matesPercent=no;
    }








}




